package irmb.flowsim.model;

import irmb.flowsim.presentation.Painter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Delaunay extends PolyLine {
    private LinkedList<Edge> edgeList;
    private LinkedList<DelaunayTriangle> triangleList;
    private Point center;
    private Point selectedPoint = null;
    private double minX1, minX2, maxX1, maxX2;

    public Delaunay() {
        this.center = new Point(0.0, 0.0);
        this.edgeList = new LinkedList<Edge>();
        this.triangleList = new LinkedList<DelaunayTriangle>();
    }

    @Override
    public void addPoint(Point p) {
        pointList.add(p);
        calculateValues();
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void removePoint(Point p) {
        pointList.remove(p);
        calculateValues();
    }

    // testet ob Koordinaten (x,y) innerhalb der Geometrie liegen
    public boolean isPointInside(double x, double y) {
        return false;
    }

    public void moveObject(double x, double y) {
        // Punkt verschieben
        if (selectedPoint != null) {
            this.selectedPoint.setX(x);
            this.selectedPoint.setY(y);
        }//
        // Dreiecke verschieben
        else {
            double dx = x - getCenterX();
            double dy = y - getCenterY();
            for (Point p : pointList) {
                p.setX(p.getX() + dx);
                p.setY(p.getY() + dy);
            }
        }
        calculateValues();
    }

    private void calculateValues() {
//        calculateCoefficients();

        minX2 = minX1 = Double.MAX_VALUE;
        maxX1 = maxX2 = -Double.MAX_VALUE;
        for (Point p : pointList) {
            if (p.getX() < minX1) {
                minX1 = p.getX();
            }
            if (p.getY() < minX2) {
                minX2 = p.getY();
            }
            if (p.getX() > maxX1) {
                maxX1 = p.getX();
            }
            if (p.getY() > maxX2) {
                maxX2 = p.getY();
            }
        }
        this.center.setX(0.5 * (maxX1 - minX1));
        this.center.setY(0.5 * (maxX2 - minX2));

        triangulate();
    }

    // berechnet Flächeninhalt
    public double calcArea() {
        return 0.0;
    }

    public double getCenterX() {
        if (selectedPoint != null) {
            return selectedPoint.getX();
        } else {
            return this.center.getX();
        }
    }

    public double getMinX() {
        return minX1;
    }

    public double getMaxX() {
        return maxX1;
    }

    public double getCenterY() {
        if (selectedPoint != null) {
            return selectedPoint.getY();
        } else {
            return center.getY();
        }
    }

    public double getMinY() {
        return minX2;
    }

    public double getMaxY() {
        return maxX2;
    }

    public double getArea() {
        return 0.0;
    }
    

    public boolean isPointOnBoundary(double x, double y, double r) {

        // Prüft ob Mausklick (x,y) auf einem Kontrollpunkt der Bezierkurve liegt
        for (Point p : pointList) {
            if ((Math.abs(x - p.getX()) < r) && (Math.abs(y - p.getY()) < r)) {
                this.selectedPoint = p;
                return true;
            }
        }
        this.selectedPoint = null;
        return false;
    }

    void addTriangle(DelaunayTriangle triangle) {
        triangleList.add(triangle);
    }

    public void addKante(Edge e) {
        edgeList.add(e);
    }

    //  Triangulations-Routinen                                                               
    private void triangulate() {

        if (pointList.size() < 3) {
            return;
        }

        triangleList.clear();

        for (Point p1 : pointList) {
            for (Point p2 : pointList) {
                for (Point p3 : pointList) {

                    if (p1 != p2 && p2 != p3 && p1 != p3) {
                        DelaunayTriangle tmpTriangle = new DelaunayTriangle(p1, p2, p3);
                        boolean tester = true;
                        for (Point p : pointList) {
                            if (p != p1 && p != p2 && p != p3) {
                                if (tmpTriangle.isPointInCircumCircle(p)) {
                                    tester = false;
                                }
                            }
                        }

                        if (tester) {
                            this.addTriangle(tmpTriangle);
                        }
                    }
                }
            }
        }
    }

    public LinkedList<Point> getEdgeList() {
        LinkedList<Point> list = new LinkedList<Point>();
        for (DelaunayTriangle tri : this.triangleList) {
            list.add(tri.e1.p1);
            list.add(tri.e1.p2);

            list.add(tri.e2.p1);
            list.add(tri.e2.p2);

            list.add(tri.e3.p1);
            list.add(tri.e3.p2);
        }       


        return list;
    }

    @Override
    public void accept(ShapeVisitor visitor) {
        visitor.visit(this);
    }

}


class Edge {

    Point p1, p2;
    LinkedList<DelaunayTriangle> my_triangles;

    Edge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.my_triangles = new LinkedList<DelaunayTriangle>();
    }

    boolean isPointOnEdge(Point v) {

        double s1 = (-2 * v.getX() + p1.getX() + p2.getX());
        double s2 = (p1.getX() - p2.getX());
        double t1 = (-2 * v.getY() + p1.getY() + p2.getY());
        double t2 = (p1.getY() - p2.getY());

        if (Math.abs(s2) < 1.0E-10) {
            if (Math.abs(s1) < 1.0E-10) {
                double t = t1 / t2;
                if (Math.abs(t) <= 1.0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else if (Math.abs(t2) < 1.0E-10) {
            if (Math.abs(t1) < 1.0E-10) {
                double s = s1 / s2;
                if (Math.abs(s) <= 1.0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            double t = t1 / t2;
            double s = s1 / s2;
            if (Math.abs(s) < 1.0 && Math.abs(s - t) < 1.E-3) {
                return true;
            }
            return false;
        }
    }

    void invalidateEdges() {
        this.my_triangles.clear();
    }

    void addTriangle(DelaunayTriangle triangle) {
        my_triangles.add(triangle);
    }

    void removeTriangles(DelaunayTriangle triangle) {
        this.my_triangles.remove(triangle);
    }

    DelaunayTriangle oppositeTriangle(DelaunayTriangle triangle) {

        if (my_triangles.size() > 2) {
            System.out.println("FEHLER!!! hab zu viele Dreiecke an einer Kante!");
            return null;
        }
        if (my_triangles.size() == 2) {
            for (DelaunayTriangle tmpTriangle : my_triangles) {
                if (tmpTriangle != triangle) {
                    return tmpTriangle;
                }
            }
        }
        return null;
    }

    boolean hasPoint(Point point) {
        if (point == p1 || point == p2) {
            return true;
        }
        return false;
    }

    void splitEdge(Point point, Delaunay del) {

        if (!isPointOnEdge(point)) {
            System.out.println("kante wird gesplittet, obwohl Punkt nicht draufliegt");
            return;
        }

        if (my_triangles.size() > 2) {
            System.out.println("Fehler: zu viele Dreiecke an Kante!");
            return;
        }

        if (my_triangles.size() == 0) {
            System.out.println("Fehler, Kante wird gesplittet, obwohl Dreiecke nicht dranhängen!");
            return;
        }

        DelaunayTriangle triangleOld = my_triangles.get(0);
        if (triangleOld.childTriangles.size() > 0) {
            System.out.println("Dreieck wird gesplittet, obwohl es Kinder hat!!!");
            return;
        }

        DelaunayTriangle triangleOld2 = my_triangles.get(1);
        if (triangleOld2.childTriangles.size() > 0) {
            System.out.println("Dreieck wird gesplittet, obwohl es Kinder hat!!!");
            return;
        }

        //neue Kanten erzeugen
        Point pointOld = triangleOld.opposite_point(this);
        Point pointOld2 = triangleOld2.opposite_point(this);

        Edge kante1neu = new Edge(point, pointOld);
        Edge kante2neu = new Edge(point, this.p1);
        Edge kante3neu = new Edge(point, this.p2);
        Edge kante4neu = new Edge(point, pointOld2);
        del.addKante(kante1neu);
        del.addKante(kante2neu);
        del.addKante(kante3neu);
        del.addKante(kante4neu);

        //neue Dreiecke erzeugen
        Edge kante1 = triangleOld.opposite_edge(p1);
        Edge kante2 = triangleOld.opposite_edge(p2);
        Edge kante3 = triangleOld2.opposite_edge(p1);
        Edge kante4 = triangleOld2.opposite_edge(p2);

        DelaunayTriangle triangle1 = new DelaunayTriangle(kante1neu, kante2neu, kante2);
        DelaunayTriangle triangle2 = new DelaunayTriangle(kante1neu, kante3neu, kante1);
        DelaunayTriangle triangle3 = new DelaunayTriangle(kante4neu, kante2neu, kante4);
        DelaunayTriangle triangle4 = new DelaunayTriangle(kante4neu, kante3neu, kante3);

        kante1neu.invalidateEdges();
        kante2neu.invalidateEdges();
        kante3neu.invalidateEdges();
        kante4neu.invalidateEdges();

        //den Kanten die neuen Dreiecke mitteilen
        kante1neu.addTriangle(triangle1);
        kante1neu.addTriangle(triangle2);

        kante2neu.addTriangle(triangle1);
        kante2neu.addTriangle(triangle3);

        kante3neu.addTriangle(triangle2);
        kante3neu.addTriangle(triangle4);

        kante4neu.addTriangle(triangle3);
        kante4neu.addTriangle(triangle4);

        //dem alten dreieck neue kinder mitteilen
        triangleOld.addChild(triangle1);
        triangleOld.addChild(triangle2);
        triangleOld2.addChild(triangle3);
        triangleOld2.addChild(triangle4);

        //alte Kanten als ungültig markieren: my_triangles = 0;
        this.invalidateEdges();

        //ungültige Dreiecke von alten Kanten entfernen
        kante1.removeTriangles(triangleOld);
        kante2.removeTriangles(triangleOld);
        kante3.removeTriangles(triangleOld2);
        kante4.removeTriangles(triangleOld2);

        //sinds noch gültige Delaunay-Dreiecke?
        triangle1.test_and_sweep_edges(point, kante2, del);
        triangle2.test_and_sweep_edges(point, kante1, del);
        triangle3.test_and_sweep_edges(point, kante4, del);
        triangle4.test_and_sweep_edges(point, kante3, del);
    }
}





class DelaunayTriangle {

    Edge e1, e2, e3;
    Point p1, p2, p3;
    ArrayList<DelaunayTriangle> childTriangles;

    DelaunayTriangle(Edge e1, Edge e2, Edge e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;

        this.p1 = e1.p1;
        this.p2 = e2.p2;
        this.p3 = e2.p1;
        if (p3 == p2 || p3 == p1) {
            this.p3 = e2.p2;
        }

        this.childTriangles = new ArrayList<DelaunayTriangle>();
    }

    DelaunayTriangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        e1 = new Edge(p1, p2);
        e2 = new Edge(p2, p3);
        e3 = new Edge(p3, p1);

        this.childTriangles = new ArrayList<DelaunayTriangle>();
    }

    void addChild(DelaunayTriangle triangle) {
        childTriangles.add(triangle);
    }

    DelaunayTriangle find_triangle(Point point) {
        if (childTriangles.size() == 0) {
            return (this);
        }
        for (DelaunayTriangle child : childTriangles) {
            if (child.point_in_triangle(point) > 0) {
                return (child.find_triangle(point));
            }
        }
        System.out.println("Dreieck (punkt einfuegen) nicht gefunden!");
        return null;
    }

    Point opposite_point(Edge kante) {
        Edge tmp;
        if (this.e1 == kante) {
            tmp = this.e2;
        } else {
            tmp = this.e1;
        }
        if (tmp.p1 == kante.p1 || tmp.p1 == kante.p2) {
            return tmp.p1;
        } else {
            return tmp.p2;
        }
    }

    Edge opposite_edge(Point point) {
        if (this.e1.p1 != point && this.e1.p2 != point) {
            return e1;
        }
        if (this.e2.p1 != point && this.e2.p2 != point) {
            return e2;
        }
        return e3;
    }

    DelaunayTriangle oppositeTriangle(DelaunayTriangle triangle) {
        if (childTriangles.size() > 2) {
            System.out.println("FEHLER!!! hab zu viele Dreiecke an einer Kante!");
            return null;
        }
        if (childTriangles.size() == 2) {
            for (DelaunayTriangle tmpTriangle : childTriangles) {
                if (tmpTriangle != triangle) {
                    return tmpTriangle;
                }
            }
        }
        return null;
    }

    //0: liegt nicht in Dreieck
    //1: liegt in Dreieck
    //2: liegt auf Kante
    int point_in_triangle(Point point) {

        if (e1.isPointOnEdge(point)) {
            return 2;
        }
        if (e2.isPointOnEdge(point)) {
            return 2;
        }
        if (e3.isPointOnEdge(point)) {
            return 2;
        }

        double xp = point.getX();
        double yp = point.getY();

        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();
        double x3 = p3.getX();
        double y3 = p3.getY();

        double denominator = (y2 * x1 - y2 * x3 + y1 * x3 - x1 * y3 - x2 * y1 + x2 * y3);

        if (Math.abs(denominator) < 1.0E-8) {
            System.out.println("Die Flaeche vom Dreieck ist null !");
        }
        denominator = 1. / denominator;

        double zae_a = (-y2 * x3 + x2 * y3 - y3 * xp + yp * x3 + y2 * xp - x2 * yp);
        double a = zae_a * denominator;
        if (a > 0.0 && a < 1.0) {
            double zae_b = -(y1 * xp - y1 * x3 - y3 * xp + x1 * y3 + yp * x3 - x1 * yp);
            double b = zae_b * denominator;
            if (b > 0.0 && b < 1.0) {
                double zae_c = (y2 * x1 + y1 * xp - x1 * yp - y2 * xp - x2 * y1 + x2 * yp);
                double c = zae_c * denominator;
                if (c > 0.0 && c < 1.0) {
                    return 1;
                }
            }
        }

        return 0;
    }

    void insertPoint(Point v, Delaunay del) {
        if (childTriangles.size() > 0) {
            System.out.println("FEHLER: Dreieck wird gesplittet, obwohl es Kinder hat!!!");
            return;
        }

        //Kanten erzeugen
        Edge edge1 = new Edge(v, p1);
        Edge edge2 = new Edge(v, p2);
        Edge edge3 = new Edge(v, p3);

        //Kantenliste
        del.addKante(edge1);
        del.addKante(edge2);
        del.addKante(edge3);

        //neue Dreiecke erzeugen
        DelaunayTriangle tri1 = new DelaunayTriangle(this.e1, edge1, edge2);
        DelaunayTriangle tri2;
        DelaunayTriangle tri3;

        if (this.e2.hasPoint(p1)) {
            tri2 = new DelaunayTriangle(this.e2, edge1, edge3);
            tri3 = new DelaunayTriangle(this.e3, edge2, edge3);
        } else {
            tri2 = new DelaunayTriangle(this.e2, edge2, edge3);
            tri3 = new DelaunayTriangle(this.e3, edge1, edge3);
        }

        //Dreiecksliste
        del.addTriangle(tri1);
        del.addTriangle(tri2);
        del.addTriangle(tri3);

        //altes Dreieck löschen
        this.e1.removeTriangles(this);
        this.e2.removeTriangles(this);
        this.e3.removeTriangles(this);
        //del->removeTriangle(this);

        //Kinderliste
        this.addChild(tri1);
        this.addChild(tri2);
        this.addChild(tri3);

        //cout << "Kinder Größe: " << my_kids.size() << endl;

        //sinds noch gültige Delaunay-Dreiecke?

        tri1.test_and_sweep_edges(v, this.e1, del);
        tri2.test_and_sweep_edges(v, this.e2, del);
        tri3.test_and_sweep_edges(v, this.e3, del);
    }

    void test_and_sweep_edges(Point new_point, Edge edge, Delaunay del) {
        if (this.childTriangles.size() > 0) {
            System.out.println("Ungueltiges Dreieck wird getestet!!!");
        }
        //finde gueltige dreiecke
        DelaunayTriangle triOld = edge.oppositeTriangle(this);

        //nur ein dreieck an kante
        if (triOld == null) {
            //cout << " nur ein Dreieck an Kante" << endl;
            return;
        }

        if (triOld.isPointInCircumCircle(new_point)) {
            Point old_point = triOld.opposite_point(edge);
            Edge new_kante = new Edge(new_point, old_point);
            del.addKante(new_kante);

            Edge tmpEdge1 = triOld.opposite_edge(edge.p1);
            Edge tmpEdge2 = opposite_edge(edge.p1);

            DelaunayTriangle t1 = new DelaunayTriangle(new_kante, tmpEdge1, tmpEdge2);
            del.addTriangle(t1);
            this.addChild(t1);
            triOld.addChild(t1);

            Edge tmpEdge3 = triOld.opposite_edge(edge.p2);
            Edge tmpEdge4 = this.opposite_edge(edge.p2);
            DelaunayTriangle t2 = new DelaunayTriangle(new_kante, tmpEdge3, tmpEdge4);
            del.addTriangle(t2);
            this.addChild(t2);
            triOld.addChild(t2);

            //Kante als ungültig markieren
            edge.invalidateEdges();

            //ungültige Dreiecke von Kanten löschen
            tmpEdge1.removeTriangles(triOld);
            tmpEdge2.removeTriangles(this);
            tmpEdge3.removeTriangles(triOld);
            tmpEdge4.removeTriangles(this);

            t1.test_and_sweep_edges(new_point, tmpEdge1, del);
            t2.test_and_sweep_edges(new_point, tmpEdge3, del);
        }
    }

    boolean isPointInCircumCircle(Point point) {

        double a1 = p1.getX();
        double a2 = p1.getY();

        double b1 = p2.getX();
        double b2 = p2.getY();

        double c1 = p3.getX();
        double c2 = p3.getY();

        double a1_sq = a1 * a1;
        double a2_sq = a2 * a2;
        double b1_sq = b1 * b1;
        double b2_sq = b2 * b2;
        double c1_sq = c1 * c1;
        double c2_sq = c2 * c2;

        double delta = 4.0 * (a1 - b1) * (b2 - c2) - 4.0 * (b1 - c1) * (a2 - b2);

        if (Math.abs(delta) < 1.E-8) {
//                System.out.println("Auf einer Linie!!!");
            return false;
        }
        double delta1 = 2.0 * (a1_sq + a2_sq - b1_sq - b2_sq) * (b2 - c2) - 2.0 * (b1_sq + b2_sq - c1_sq - c2_sq) * (a2 - b2);
        double delta2 = 2.0 * (b1_sq + b2_sq - c1_sq - c2_sq) * (a1 - b1) - 2.0 * (a1_sq + a2_sq - b1_sq - b2_sq) * (b1 - c1);

        double x1 = delta1 / delta;
        double x2 = delta2 / delta;

        double r = Math.sqrt((a1 - x1) * (a1 - x1) + (a2 - x2) * (a2 - x2));

        double u1 = point.getX();
        double u2 = point.getY();

        double ru = Math.sqrt((x1 - u1) * (x1 - u1) + (x2 - u2) * (x2 - u2));

        if (ru < r) {
            return true;
        }

        return false;
    }
}
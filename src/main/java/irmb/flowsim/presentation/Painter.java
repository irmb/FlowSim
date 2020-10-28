package irmb.flowsim.presentation;

import irmb.flowsim.model.Point;

/**
 * Created by Sven on 15.12.2016.
 */
public interface Painter {
    void paintLine(double x1, double y1, double x2, double y2);

    void paintLine(Point first, Point second);

    void paintRectangle(double x, double y, double width, double height);

    void paintRectangle(Point origin, double width, double height);

    void paintCircle(double x, double y, double radius);

    void paintCircle(Point center, double radius);

    void paintTriangle(double x, double y, double width, double height);

    void setColor(Color color);

    void fillRectangle(double x, double y, double width, double height);

    void paintPoint(double x, double y);

    void paintPoint(Point point);

    void paintString(String s, double x, double y);
}

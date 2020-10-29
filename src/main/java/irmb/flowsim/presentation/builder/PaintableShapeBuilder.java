package irmb.flowsim.presentation.builder;

import irmb.flowsim.model.Point;
import irmb.flowsim.view.graphics.PaintableShape;

/** Created by Sven on 14.12.2016. */
public abstract class PaintableShapeBuilder {

    public abstract void addPoint(Point point);

    public abstract PaintableShape getShape();

    public abstract void setLastPoint(Point lastPoint);

    public abstract void removeLastPoint();

    public abstract boolean isObjectFinished();

    public abstract boolean isObjectPaintable();

    public abstract boolean isInfinite();
}

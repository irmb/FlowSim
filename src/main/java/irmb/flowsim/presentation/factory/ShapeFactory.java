package irmb.flowsim.presentation.factory;

import irmb.flowsim.model.Shape;

public interface ShapeFactory {
    Shape makeShape(String shape);
}

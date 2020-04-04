package irmb.flowsim.presentation.factory;

import irmb.flowsim.model.MultiPointShape;

/**
 * Created by Sven on 14.12.2016.
 */
public interface MultiPointShapeFactory {
    MultiPointShape makeShape(String type);
}

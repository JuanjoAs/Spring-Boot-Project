package com.juanjo.proyecto.repository;


import java.util.List;
import java.util.Map;

import com.juanjo.proyecto.model.Canvas;


public class CanvasRepositoryImpl implements CanvasRepository {

	@Override
	public List<List<Map<Object, Object>>> getCanvas() {
		return Canvas.getCanvasjsDataList();
	}

}  
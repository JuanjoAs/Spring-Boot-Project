package com.juanjo.proyecto.service;

import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanjo.proyecto.model.Canvas;
import com.juanjo.proyecto.repository.CanvasRepository;
 


public class CanvasServiceImpl implements CanvasService {
 
	@Autowired
	private CanvasRepository canvasjsChartRepository;
 
	
 
	@Override
	public List<List<Map<Object, Object>>> getCanvasjsChartData() {
		return Canvas.getCanvasjsDataList();
		}
 
}    
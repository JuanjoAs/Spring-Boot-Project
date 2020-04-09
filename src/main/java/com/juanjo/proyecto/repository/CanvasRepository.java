package com.juanjo.proyecto.repository;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juanjo.proyecto.model.Canvas;
@Repository("canvasRepository")
public interface CanvasRepository {
	List<List<Map<Object, Object>>> getCanvas();
}
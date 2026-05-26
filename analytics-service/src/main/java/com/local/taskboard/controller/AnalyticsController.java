package com.local.taskboard.controller;

import com.local.taskboard.entity.BoardAnalytics;
import com.local.taskboard.service.AnalyticsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for retrieving board analytics data.
 * Exposes endpoints to get statistics for all boards or a specific board.
 */
@RestController
@RequestMapping("/api/analytics")
@AllArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping()
    public ResponseEntity<Object> getAnalytics() {
        try {
            List<BoardAnalytics> listboardAnalytics = analyticsService.getBoardAnalytics();
            return new ResponseEntity<>(listboardAnalytics, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<Object> getAnalyticsByBoardId(@PathVariable String boardId) {
        try {
            BoardAnalytics boardAnalytics = analyticsService.getBoardAnalyticsById(boardId);
            return new ResponseEntity<>(boardAnalytics, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

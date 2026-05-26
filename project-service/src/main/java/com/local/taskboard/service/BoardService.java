package com.local.taskboard.service;

import com.local.taskboard.controller.ProjectController;
import com.local.taskboard.domain.Board;
import com.local.taskboard.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing board-related operations in the TaskBoard
 * platform.
 * This service provides business logic for creating, retrieving, and managing
 * boards
 * which represent collections of cards/tasks organized in columns.
 *
 * <p>
 * The service acts as an intermediary between the BoardController and
 * BoardRepository,
 * handling the business logic and data transformations required for board
 * operations.
 *
 * @author TaskBoard Platform Team
 * @version 1.0
 * @since 1.0
 */
@Service
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    private final MongoTemplate mongoTemplate;

    public BoardService(BoardRepository boardRepository, MongoTemplate mongoTemplate) {
        this.boardRepository = boardRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<Board> findById(String id) {
        return boardRepository.findById(id);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public List<Board> findAllByUser(String user) {
        log.info("Searching boards for user={}", user);

        return mongoTemplate.find(new org.springframework.data.mongodb.core.query.Query(
                Criteria.where("ownerUserId").is(user)
        ), Board.class);

    }

    public Board save(ProjectController.BoardRequest board) {
        Board savedBoard = Board.builder()
                .name(board.name())
                .description(board.description())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .ownerUserId(board.ownerUserId()).build();

        return boardRepository.save(savedBoard);
    }
}

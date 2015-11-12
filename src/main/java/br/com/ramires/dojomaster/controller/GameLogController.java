package br.com.ramires.dojomaster.controller;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import br.com.ramires.dojomaster.dto.GameRanksDTO;
import br.com.ramires.dojomaster.exception.GameLogDefaultException;
import br.com.ramires.dojomaster.exception.GameLogParserException;
import br.com.ramires.dojomaster.exception.GameLogRepositoryException;
import br.com.ramires.dojomaster.exception.GameLogValidatorException;
import br.com.ramires.dojomaster.service.GameLogService;

@RestController
@RequestMapping("/dojomaster")
@Api(description = "The GameLog Controller to send a new game logs", name = "GameLog Services")
public class GameLogController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameLogController.class);

	@Autowired
	private GameLogService service;

	@ApiMethod(description = "Send a game log")
    @RequestMapping(value="/sendGameLog", method = RequestMethod.POST)
	public @ApiResponseObject @ResponseBody ResponseEntity<String> receiveGameLog(@ApiBodyObject @RequestBody MultipartFile gameFileLog) {

		LOGGER.debug("GameLogController->receiveGameLog->begin()");
		LOGGER.warn("GameLogController->receiveGameLog-> Request received, run the service");
		LOGGER.debug(String.format("GameLogController->receiveGameLog-> Request: %s", gameFileLog.getName()));

		GameRanksDTO gameRanksDTO = service.run(gameFileLog);
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(gameRanksDTO.getRanksReports(), HttpStatus.CREATED);

		LOGGER.warn("GameLogController->receiveGameLog-> Response generated, send back");
		LOGGER.debug(String.format("GameLogController->receiveGameLog-> Response: %s", responseEntity));
		LOGGER.debug("GameLogController->receiveGameLog->end()");
		return responseEntity;
	}

	@ExceptionHandler
    public ResponseEntity<String> handleGameLogDefaultException(GameLogDefaultException e)  {

		LOGGER.error(String.format("GameLogController->handleGameLogDefaultException-> Received Exception with the message: %s", e));
    	return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

	@ExceptionHandler
	public ResponseEntity<String> handleGameLogRepositoryException(GameLogRepositoryException e)  {

		LOGGER.error(String.format("GameLogController->handleGameLogRepositoryException-> Received Exception with the message: %s", e.getMessage()));
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
    public ResponseEntity<String> handleGameLogValidatorException(GameLogValidatorException e)  {

		LOGGER.error(String.format("GameLogController->handleGameLogValidatorException-> Received Exception with the message: %s", e));
    	return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler
    public ResponseEntity<String> handleGameLogParserException(GameLogParserException e)  {

		LOGGER.error(String.format("GameLogController->handleGameLogParserException-> Received Exception with the message: %s", e));
    	return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

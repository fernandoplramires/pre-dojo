package br.com.ramires.dojomaster.service;

import org.springframework.web.multipart.MultipartFile;

import br.com.ramires.dojomaster.dto.GameRanksDTO;

public interface GameLogService {

	public GameRanksDTO run(final MultipartFile fileInput);
}

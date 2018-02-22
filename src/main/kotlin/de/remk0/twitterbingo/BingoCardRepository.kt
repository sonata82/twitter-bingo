package de.remk0.twitterbingo

import org.springframework.data.repository.CrudRepository

interface BingoCardRepository : CrudRepository<BingoCard, String>
package de.remk0.twitterbingo
import org.springframework.data.annotation.Id;

data class BingoCard(@Id var id: String, var size: Int, var words: List<String>)
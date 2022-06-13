package blackjack.view

import blackjack.domain.participant.Money
import blackjack.domain.participant.Player

object InputView {
    fun inputPlayers(): List<Player> {
        println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)")
        val playerNames = requireNotNull(readlnOrNull())

        require(playerNames.isNotBlank()) { "플레이어의 이름은 공백일 수 없습니다." }
        return playerNames.split(PLAYER_INPUT_DELIMITER).map { name ->
            println("$name 의 베팅 금액은?")
            val batMoney = requireNotNull(readlnOrNull()?.toIntOrNull()) { "잘못된 베팅 금액입니다." }
            Player(name, Money(batMoney))
        }
    }

    fun decidePlayerHitDecision(player: Player): Boolean {
        println("${player.name}은(는) 한장의 카드를 더 받겠습니까? (y, n)")
        return when (readlnOrNull()) {
            "y" -> true
            "n" -> false
            else -> throw IllegalArgumentException("잘못된 값을 입력했습니다. y 또는 n 으로 선택해주세요.")
        }
    }

    private const val PLAYER_INPUT_DELIMITER = ","
}

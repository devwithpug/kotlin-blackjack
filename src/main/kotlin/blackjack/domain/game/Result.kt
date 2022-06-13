package blackjack.domain.game

import blackjack.domain.participant.Dealer
import blackjack.domain.participant.Participant
import blackjack.domain.participant.ParticipantStatus
import blackjack.domain.participant.Player

data class Result(
    private val dealer: Dealer,
    private val players: List<Player>,
    private val _scoreByParticipantName: MutableMap<String, Score> = mutableMapOf(),
    private val _totalEarningsByParticipantName: MutableMap<String, Int> = mutableMapOf()
) {
    val scoreByParticipantName: Map<String, Score>
        get() = _scoreByParticipantName.map { it.key to it.value.copy() }.toMap()
    val totalEarningsByParticipantName: Map<String, Int>
        get() {
            val totalEarningsByParticipantName = mutableMapOf(dealer.name to dealer.money.bat)
            players.forEach { player -> totalEarningsByParticipantName[player.name] = player.money.bat }
            return totalEarningsByParticipantName
        }

    init {
        _scoreByParticipantName[dealer.name] = Score()
        players.forEach { player -> _scoreByParticipantName[player.name] = Score() }
    }

    fun decideWinner(dealer: Dealer, player: Player) {
        if (dealer.status != ParticipantStatus.BLACKJACK && player.status == ParticipantStatus.BLACKJACK) {
            val playerAccMoney = (player.money.bat * BLACKJACK_PROFIT_PERCENTAGE).toInt()
            win(player, winMoney = playerAccMoney)
            lose(dealer, loseMoney = playerAccMoney)
            return
        }
        if (dealer.status == ParticipantStatus.BUST) {
            win(player)
            lose(dealer)
            return
        }
        if (player.status == ParticipantStatus.BUST) {
            win(dealer, player.money.bat)
            lose(player, player.money.bat)
            return
        }

        if (dealer.score() > player.score()) {
            win(dealer, player.money.bat)
            lose(player, player.money.bat)
        }
        if (dealer.score() < player.score()) {
            win(player)
            lose(dealer)
        }
    }

    private fun win(participant: Participant, winMoney: Int = 0) {
        updateParticipantResultCount(participant, isWin = true)
        participant.money.accBatMoney(winMoney)
    }

    private fun lose(participant: Participant, loseMoney: Int = 0) {
        updateParticipantResultCount(participant, isWin = false)
        participant.money.accBatMoney(-loseMoney)
    }

    private fun updateParticipantResultCount(participant: Participant, isWin: Boolean) {
        val score =
            _scoreByParticipantName[participant.name] ?: throw IllegalStateException("participant must be added to map")
        if (isWin) {
            score.win += 1
        } else {
            score.lose += 1
        }
    }

    companion object {
        const val BLACKJACK_PROFIT_PERCENTAGE = 0.5
    }
}

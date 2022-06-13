package blackjack

import blackjack.domain.card.Card
import blackjack.domain.card.CardSuit
import blackjack.domain.card.CardSymbol
import blackjack.domain.participant.Money

object CardFixtures {
    val DIAMOND_ACE = Card(CardSuit.DIAMOND, CardSymbol.ACE)
    val CLUB_KING = Card(CardSuit.CLUB, CardSymbol.KING)
    val SPADE_TEN = Card(CardSuit.SPADE, CardSymbol.TEN)
    val SPADE_FIVE = Card(CardSuit.SPADE, CardSymbol.FIVE)
    val HEART_SIX = Card(CardSuit.HEART, CardSymbol.SIX)
    val HEART_TWO = Card(CardSuit.HEART, CardSymbol.TWO)

    val MONEY = Money(1000)
}

package blackjack.player

import blackjack.card.Card

data class Hand(
    val cards: MutableList<Card> = mutableListOf()

) {

    fun add(card: Card): Hand {
        cards.add(card)
        return this
    }

    fun score(): Int {
        return cards
            .sortedBy { it.symbol }
            .fold(0) { acc, card -> acc + card.count(acc) }
    }

    override fun toString(): String {
        return cards.joinToString()
    }
}

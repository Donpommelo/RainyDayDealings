package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.RainyDayGame;
import com.mygdx.game.stuff.Card;

public class CardActor extends ARDDActor {

	private Card card;
	
	public static final int cardWidth = 75;
	public static final int cardHeight = 105;
	
	public CardActor(Card card) {
		super(0, 0, cardWidth, cardHeight);
		this.card = card;
	}

	@Override
    public void draw(Batch batch, float alpha) {
         
//		batch.setProjectionMatrix(ps.sprite.combined);
		RainyDayGame.SYSTEM_FONT_SPRITE.draw(batch, card.getCardText(), getX(), getY() + cardHeight);
    }
	
	public Card getCard() {
		return card;
	}
}

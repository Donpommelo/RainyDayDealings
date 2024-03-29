package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.RainyDayGame;

/**
 * Simple actor that displays floating text. Not suitable for long messages.
 */
public class Text extends ARDDActor {

	protected String text;
	protected BitmapFont font;
	protected Color color;
	protected GlyphLayout layout;

	protected float scale = 1.0f;
	
	public Text(String text, int x, int y) {
		super(x, y);
		this.text = text;
		font = RainyDayGame.SYSTEM_FONT_UI;
		color = RainyDayGame.DEFAULT_TEXT_COLOR;
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		updateHitBox();
	}
	
	public Text(String text, int x, int y, Color color) {
		this(text, x, y);
		this.color = color;
	}
	
	@Override
    public void draw(Batch batch, float alpha) {		
		 font.getData().setScale(scale);
		 font.setColor(color);
         font.draw(batch, text, getX(), getY() + layout.height);
         
         //Return scale and color to default values.
         font.getData().setScale(1.0f);
         font.setColor(RainyDayGame.DEFAULT_TEXT_COLOR);
    }
	
	@Override
	public void updateHitBox() {
		font.getData().setScale(scale);
		layout = new GlyphLayout(font, text);
		setWidth(layout.width);
		setHeight(layout.height);
		super.updateHitBox();
		font.getData().setScale(1.0f);
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		updateHitBox();
	}
	
	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	public float getScale() {
		return scale;
	}

	@Override
	public void setScale(float scale) {
		this.scale = scale;
		updateHitBox();
	}
}

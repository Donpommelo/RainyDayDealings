package com.mygdx.game.actors;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.states.PlayState;

public class ActionLog {

	private final static int logWidthOuter = 210;
	private final static int logWidthInner = 200;
	private final static int logHeight = 100;
	private final static int logEntryHeight = 15;
	
	private Table tableInner, tableOuter;
	private ScrollPane options;
	private Text title, toggle;
	private boolean visible;
	
	public ActionLog(PlayState ps) {
		this.visible = true;
		this.tableInner = new Table();
		this.tableOuter = new Table();
		
		title = new Text("LOG", 0, 0);
		title.setScale(0.5f);
		tableOuter.add(title).width(logWidthOuter).height(logEntryHeight);
		tableOuter.row();
		this.options = new ScrollPane(tableInner, ps.getGsm().getSkin());
		options.setFadeScrollBars(false);
		tableOuter.add(options).width(logWidthOuter).height(logHeight);
		
		tableOuter.setPosition(0, 0);
		tableOuter.setSize(logWidthOuter, logHeight);
		
		toggle = new Text("SHOW", logWidthOuter, 0);
		toggle.setScale(0.5f);
		toggle.addListener(new ClickListener() {
			
			@Override
	        public void clicked(InputEvent e, float x, float y) {
				toggleVisibility();
	        }
	    });
		
		ps.getStage().addActor(tableOuter);
		ps.getStage().addActor(toggle);
	}
	
	public void toggleVisibility() {
		if (visible) {
			tableOuter.addAction(Actions.moveTo(0, logEntryHeight - logHeight, .5f, Interpolation.pow5Out));
		} else {
			tableOuter.addAction(Actions.moveTo(0, 0, .5f, Interpolation.pow5Out));
		}
		visible = !visible;
	}
	
	public void addAction(String wurds) {
		Text text = new Text(wurds, 0, 0);
		text.setScale(0.25f);
		tableInner.add(text).width(logWidthInner).height(logEntryHeight);
		tableInner.row();
		options.scrollTo(0, 0, 0, 0);
	}
}

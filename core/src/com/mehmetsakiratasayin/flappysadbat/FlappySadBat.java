package com.mehmetsakiratasayin.flappysadbat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sun.java.swing.plaf.windows.WindowsTreeUI;

import java.util.Random;

import javax.naming.Context;

public class FlappySadBat extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bat;
	Texture robot1;
	Texture robot2;
	Texture ufocuk;
	Texture robot4;
	Texture robot5;
	float batX=0;
	float batY=0;
	int gameState=0;
	float velocity=0;
	float gravity= 1.2f;
	Random random;
	int count=0;
	int countEnemy=0;
	int Click;
	int highscore;
	BitmapFont bitmapFont;
	BitmapFont bitmapFont2;
	BitmapFont bitmapFont3;
	float randomNumber;
	private Sound sound;
	private Preferences preferences;

	Circle batCircle;
	ShapeRenderer shapeRenderer;


	int numberOffEnemies=4;
	float [] enemyX =new float[numberOffEnemies];
	float [] enemyOffset =new float[numberOffEnemies];
	float [] enemyOffset2 =new float[numberOffEnemies];
	float [] enemyOffset3 =new float[numberOffEnemies];
	float [] enemyOffset4=new float[numberOffEnemies];
	float [] enemyOffset5=new float[numberOffEnemies];
	float distance=0;
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;
	Circle[] enemyCircles4;
	Circle[] enemyCircles5;
	float enemyVelocity=2;



	@Override
	public void create () {
		preferences = Gdx.app.getPreferences("com.mehmetsakiratasayin.flappybat");
		Click=0;

		batch=new SpriteBatch();
		background=new Texture("background.png");
		bat=new Texture("bat.png");
		robot1=new Texture("robot.png");
		robot2=new Texture("robot.png");
		ufocuk=new Texture("ufocuk.png");
		robot4=new Texture("robot.png");
		robot5=new Texture("robot.png");
		distance=Gdx.graphics.getWidth()/4;
		random=new Random();


		batX=Gdx.graphics.getWidth()/2-bat.getHeight()/2;
		batY=Gdx.graphics.getHeight()/3;
		shapeRenderer=new ShapeRenderer();

		bitmapFont=new BitmapFont();
		bitmapFont.setColor(Color.WHITE);
		bitmapFont.getData().setScale(4);

		bitmapFont2=new BitmapFont();
		bitmapFont2.setColor(Color.WHITE);
		bitmapFont2.getData().setScale(4);


		bitmapFont3=new BitmapFont();
		bitmapFont3.setColor(Color.WHITE);
		bitmapFont3.getData().setScale(5);

		batCircle = new Circle();
		enemyCircles = new Circle[numberOffEnemies];
		enemyCircles2 = new Circle[numberOffEnemies];
		enemyCircles3 = new Circle[numberOffEnemies];
		enemyCircles4 = new Circle[numberOffEnemies];
		enemyCircles5 = new Circle[numberOffEnemies];

		sound =Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));


		for(int i=0; i<numberOffEnemies;i++){
			randomNumber=random.nextFloat();
			enemyOffset[i]=(randomNumber-0.2f)*(Gdx.graphics.getHeight()-200);
			enemyOffset2[i]=(randomNumber-0.4f)*(Gdx.graphics.getHeight()-200);
			enemyOffset3[i]=(randomNumber-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOffset4[i]=(randomNumber-0.6f)*(Gdx.graphics.getHeight()-200);
			enemyOffset5[i]=(randomNumber-0.8f)*(Gdx.graphics.getHeight()-200);

			enemyX[i]=Gdx.graphics.getWidth()-robot1.getWidth()/2+i*distance;


			enemyCircles[i]=new Circle();
			enemyCircles2[i]=new Circle();
			enemyCircles3[i]=new Circle();
			enemyCircles4[i]=new Circle();
			enemyCircles5[i]=new Circle();
		}




	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		highscore=preferences.getInteger("highscore",0);
		if (count > highscore) {
			preferences.putInteger("highscore", count);
			preferences.flush();
		}

		if(gameState==1){
			Click=1;

			if (enemyX[countEnemy] < Gdx.graphics.getWidth() / 2 - bat.getHeight() / 2) {
				count++;

				if (countEnemy < numberOffEnemies - 1) {
					countEnemy++;
				} else {
					countEnemy = 0;
					highscore = preferences.getInteger("highscore");
					if (count > highscore) {
						preferences.putInteger("highscore", highscore);
						preferences.flush();
					}

				}

			}


			if(Gdx.input.justTouched()){

				velocity=-14;
			}
			for(int i=0;i<numberOffEnemies;i++){
				if(enemyX[i]< Gdx.graphics.getWidth()/15){
					enemyX[i]=enemyX[i]+numberOffEnemies*distance;
					randomNumber=random.nextFloat();
					enemyOffset[i]=(randomNumber-0.2f)*(Gdx.graphics.getHeight()-200);
					enemyOffset2[i]=(randomNumber-0.4f)*(Gdx.graphics.getHeight()-200);
					enemyOffset3[i]=(randomNumber-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffset4[i]=(randomNumber-0.6f)*(Gdx.graphics.getHeight()-200);
					enemyOffset5[i]=(randomNumber-0.8f)*(Gdx.graphics.getHeight()-200);

				}
				else {
					enemyX[i]=enemyX[i]-enemyVelocity;
				}
				enemyX[i]=enemyX[i]-enemyVelocity;
				batch.draw(robot1,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(robot2,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(ufocuk,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(robot4,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset4[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
				batch.draw(robot5,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset5[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffset2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffset3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles4[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffset4[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
				enemyCircles5[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffset5[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

			}







			if(batY>0 && batY<Gdx.graphics.getHeight()){

				velocity=velocity+gravity;
				batY=batY-velocity;
			}
			else{
			//	long id =sound.play(1.0f);
			//	sound.setPitch(id,2);
			//	sound.setLooping(id,false);
				gameState=2;
			}


		}
		else if(gameState==0){
			if(Gdx.input.justTouched()){

				gameState=1;
			}
		}
		else if (gameState == 2) {
			if(Click==1){
				long id2 =sound.play(1.0f);
				sound.setPitch(id2,2);
				sound.setLooping(id2,false);
				Click=3;
				//gameState=2;

			}
			else{}
			bitmapFont2.draw(batch,"Game Over! Tap To Play Again! " ,100,Gdx.graphics.getHeight() / 2);
			bitmapFont3.draw(batch,"Max: "+highscore,100,1000);

			if (Gdx.input.justTouched()) {
				gameState = 1;

				batY = Gdx.graphics.getHeight() / 3;


				for (int i = 0; i<numberOffEnemies; i++) {
					randomNumber=random.nextFloat();
					enemyOffset[i]=(randomNumber-0.2f)*(Gdx.graphics.getHeight()-200);
					enemyOffset2[i]=(randomNumber-0.4f)*(Gdx.graphics.getHeight()-200);
					enemyOffset3[i]=(randomNumber-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOffset4[i]=(randomNumber-0.6f)*(Gdx.graphics.getHeight()-200);
					enemyOffset5[i]=(randomNumber-0.8f)*(Gdx.graphics.getHeight()-200);
					enemyX[i] = Gdx.graphics.getWidth() - robot1.getWidth() / 2 + i * distance;


					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();
					enemyCircles4[i] = new Circle();
					enemyCircles5[i] = new Circle();

				}

				velocity = 0;
				countEnemy = 0;
				count = 0;

			}
		}


		batch.draw(bat,batX,batY,Gdx.graphics.getWidth()/14,Gdx.graphics.getHeight()/9 );
		bitmapFont.draw(batch,String.valueOf(count),100,200);

		batch.end();

		batCircle.set(batX+Gdx.graphics.getWidth()/27,batY+Gdx.graphics.getWidth()/30,Gdx.graphics.getWidth()/40);
//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//shapeRenderer.setColor(Color.BLACK);
//shapeRenderer.circle(batCircle.x,batCircle.y,batCircle.radius);

		for ( int i = 0; i < numberOffEnemies; i++) {
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffset2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffset3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffset4[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			if (Intersector.overlaps(batCircle,enemyCircles[i]) || Intersector.overlaps(batCircle,enemyCircles2[i]) || Intersector.overlaps(batCircle,enemyCircles3[i]) || Intersector.overlaps(batCircle,enemyCircles4[i]) || Intersector.overlaps(batCircle,enemyCircles5[i])) {


				//deneme=1;
gameState=2;

			}
			else{

			}
		}

		//shapeRenderer.end();
	}

	@Override
	public void dispose () {

	}
}

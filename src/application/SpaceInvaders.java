package application;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener{

	
		// TODO Auto-generated constructor stub
	
		class Block {
			int x;
			int y;
			int width;
			int height;
			Image img;
			boolean alive = true; //just for bad carathers
			boolean used = false ;//used for bullets
			
			Block(int x,int y,int width,int height,Image img){
				
				this.x = x;
				this.y = y;
				this.width = width;
				this.height = height;
				this.img = img;
				
				
				
				
			}
			
		}
	
	
		//board
		int titleSize = 32;
		int rows = 16;
		int columns = 16;
		int boardWidth = titleSize * columns;
		int boardHeight = titleSize *rows;
		
		Image shipingImg;
		Image alieningImg;
		Image noob1Img;
		Image ermacImg;
		Image sindelImg;
		Image shaoImg;
		Image scorpionImg;
		
		ArrayList<Image>mortalImgArray;
		
		// noob
		int noobWidth = titleSize*2;//64px
		int noobHeight = titleSize;//32px
		
		int noobX = titleSize * columns/2 - titleSize;
		int noobY = boardHeight - titleSize*2;
		int noobVelocityX = titleSize;
		
		Block noob1;
		
		
		// caracthers
		ArrayList<Block>mortalArray;
		int mortalWidth = titleSize*2;
		int mortalHeight = titleSize;
		int mortalX = titleSize;
		int mortalY = titleSize;
		
		int mortalRows = 2;
		int mortalColumns = 3;//numero de caraters para atacar
		int mortalCount = 0;
		
		int mortalVelocityX = 1;	//mortal move velocidade	
		
		
		
		//bullets
		ArrayList<Block> bulletArray;
		int bulletWidth = titleSize/8;
		int bulletHeight = titleSize/2;
		int bulletVelocityY = -10;
	
			
		Timer gameLoop;
		int score = 0;
		boolean gameOver = false;
		
		SpaceInvaders(){
			setPreferredSize(new Dimension(boardWidth,boardHeight));
			setBackground(Color.black);
			setFocusable(true);
			addKeyListener(this);
			
				//laod images
			noob1Img = new ImageIcon(getClass().getResource("./noob1.png")).getImage();
			ermacImg = new ImageIcon(getClass().getResource("./ermac.png")).getImage();
			scorpionImg = new ImageIcon(getClass().getResource("./scorpion.png")).getImage();
			shaoImg = new ImageIcon(getClass().getResource("./shao.png")).getImage();
			sindelImg = new ImageIcon(getClass().getResource("./sindel.png")).getImage();
			
			mortalImgArray = new ArrayList<Image>();
			mortalImgArray.add(ermacImg);
			mortalImgArray.add(shaoImg);
			mortalImgArray.add(sindelImg);
			mortalImgArray.add(scorpionImg);
			
			
			noob1 = new Block(noobX,noobY,noobWidth,noobHeight,noob1Img);
			mortalArray = new ArrayList<Block>();
			bulletArray = new ArrayList<Block>();
			
			
			//game timer
			gameLoop = new Timer(1000/60, this);
			createMortais();
			gameLoop.start();
			
		}
		
		
		
		public void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			draw(g);

		}
		public void draw(Graphics g) {
			g.drawImage(noob1.img,noob1.x,noob1.y, noob1.width, noob1.height,null);
		
			//aliens mortais
			for(int i = 0;i< mortalArray.size();i++) {
				Block mortal = mortalArray.get(i);
				if(mortal.alive) {
					g.drawImage(mortal.img,mortal.x,mortal.y,mortal.width,mortal.height,null);
					
				}
			}
			
			g.setColor(Color.white);
			for(int i = 0;i < bulletArray.size();i++) {
				Block bullet = bulletArray.get(i);
				
				if(!bullet.used) {
				
					//g.drawRect(bullet.x,bullet.y,bullet.width,bullet.height);
					g.fillRect(bullet.x,bullet.y,bullet.width,bullet.height);
					
					
				
				}
				
			}
			
			//score
			g.setColor(Color.white);
			g.setFont(new Font("Arial",Font.PLAIN,32));
			if(gameOver) {
				g.drawString("Game Over: "+String.valueOf(score),10,35);
			}
			else {
				g.drawString(String.valueOf(score),10,35);
			}
			
		
		}
		
		public void move() {
			//mortais
			for(int i = 0;i< mortalArray.size();i++) {
				Block mortal = mortalArray.get(i);
				if(mortal.alive) {
					mortal.x += mortalVelocityX;
					
					//se o caracter toca a borda
					if(mortal.x + mortal.width >= boardWidth ||mortal.x <= 0) {
						mortalVelocityX *= -1;
						mortal.x += mortalVelocityX * 2;
						
						//mover todos para baixo a cada linha
						for(int j = 0;j < mortalArray.size();j++) {
							mortalArray.get(j).y += mortalHeight;
							
						}
						
					}
					if(mortal.y >= noob1.y ) {
						gameOver = true;
					}
					
				}
			}
			
			//bullets
			for(int i =0;i < bulletArray.size();i++) {
				Block bullet = bulletArray.get(i);
				bullet.y += bulletVelocityY;
				
				
				//bullet collision with aliens
				for(int j =0;j<mortalArray.size();j++) {
					Block mortal = mortalArray.get(j);
					if(!bullet.used && mortal.alive && detectionCollision(bullet,mortal)) {
						
						bullet.used = true;
						mortal.alive = false;
						mortalCount--;
						score +=100;
						
						
						
						
					}
				}
								
			}
			//clear bullet
			while (bulletArray.size()> 0 &&(bulletArray.get(0).used || bulletArray.get(0).y <0 )) {
				bulletArray.remove(0); //remove o primeiro elemento do vetor
				
				
				
			}
			//next level
			if(mortalCount == 0) {
				// aumentar o numero de caraters na coluna e linha by 1
				
				score += mortalColumns * mortalRows * 100;
				mortalColumns = Math.min(mortalColumns + 1,  columns /2 -2);//cap column at 16/2 -2 = 6
				mortalRows = Math.min(mortalRows+1, rows - 6); // cap row at 16-6=10
				
				mortalArray.clear();
				bulletArray.clear();
				mortalVelocityX = 1 ;
				createMortais();
				
				
			}
		}

		public void createMortais() {
			Random random = new Random();
			for(int r = 0;r < mortalRows;r++) {
				for(int c =0;c < mortalColumns;c++) {
					int randomImgIndex = random.nextInt(mortalImgArray.size());
					Block mortal = new Block(mortalX + c*mortalWidth,mortalY + r*mortalHeight,mortalWidth,mortalHeight,mortalImgArray.get(randomImgIndex));
					mortalArray.add(mortal);
					
				}
			}
			mortalCount = mortalArray.size();
			
			
			
		}
		
		public boolean detectionCollision(Block a,Block b) {
			return a.x < b.x + b.width && //a's top left corner doens't reach b's top right corner
					a.x + a.width > b.x &&//a's top right corner doens't passes b's top left corner
					a.y < b.y + b.height &&//a's top left corner doens't reach b's bottom right left
					a.y + a.height < b.y;//a's bottom left corner passes b's top left corner
			
					
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			move();
			
			repaint();
			
			if(gameOver) {
				gameLoop.stop();
			}
			
			
		}



		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}



		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
			if(gameOver) {
				noob1.x = noobX;
				mortalArray.clear();
				bulletArray.clear();
				score = 0;
				mortalVelocityX = 1;
				mortalColumns = 3;
				mortalRows = 2;
				gameOver = false;
				createMortais();
				gameLoop.start();
				
				
				
				
			}
			
			if(e.getKeyCode()==KeyEvent.VK_LEFT && noob1.x - noobVelocityX >= 0 ) {
				noob1.x -= noobVelocityX;
			}else if(e.getKeyCode() == KeyEvent.VK_RIGHT && noob1.x + noob1.width + noobVelocityX <= boardWidth) {
				
				noob1.x += noobVelocityX;
				
			}else if(e.getKeyCode()== KeyEvent.VK_SPACE) {
				
				Block bullet = new Block(noob1.x + noobWidth*15/32,noob1.y,bulletWidth,bulletHeight,null);
				bulletArray.add(bullet);
			}
			
			
			
		}
		
		
		

}

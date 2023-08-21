public class Body {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Body(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b) {
		return Math.sqrt((xxPos - b.xxPos)*(xxPos - b.xxPos) + (yyPos - b.yyPos)*(yyPos - b.yyPos));
	}

	public double calcForceExertedBy(Body b) {
		double g = 6.67e-11;
		return (g*mass*b.mass) / (this.calcDistance(b)*this.calcDistance(b));
	}

	public double calcForceExertedByX(Body b) {
		double totalforce = this.calcForceExertedBy(b);
		double distance = this.calcDistance(b);
		return totalforce*(b.xxPos-this.xxPos)/distance;
	}

	public double calcForceExertedByY(Body b) {
		double totalforce = this.calcForceExertedBy(b);
		double distance = this.calcDistance(b);
		return totalforce*(b.yyPos-this.yyPos)/distance;
	}

	public double calcNetForceExertedByX(Body[] allBodys) {
		double sum = 0.0;
		for (int i = 0; i < allBodys.length; i = i + 1) {
			if (this.equals(allBodys[i])) {
				continue;
			}
			sum += this.calcForceExertedByX(allBodys[i]);
		}
		return sum;
	}

	public double calcNetForceExertedByY(Body[] allBodys) {
		double sum = 0.0;
		for (int i = 0; i < allBodys.length; i = i + 1) {
			if (this.equals(allBodys[i])) {
				continue;
			}
			sum += this.calcForceExertedByY(allBodys[i]);
		}
		return sum;
	}

	public void update(double dt, double fX, double fY) {
		double aX = fX / this.mass;
		double aY = fY / this.mass;
		this.xxVel += dt * aX;
		this.yyVel += dt * aY;
		this.xxPos += dt * this.xxVel;
		this.yyPos += dt * this.yyVel;
	}

	public void draw() {
		StdDraw.enableDoubleBuffering();
		StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
		StdDraw.show();
	}
}
























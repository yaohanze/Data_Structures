public class NBody {
	public static double readRadius(String name) {
		In readplanet = new In(name);
		readplanet.readInt();
		double radius = readplanet.readDouble();
		return radius;
	}

	public static Body[] readBodies(String name) {
		In planet = new In(name);
		int number = planet.readInt();
		planet.readDouble();
		Body[] planets = new Body[number];
		for (int i = 0; i < number; i = i + 1) {
			planets[i] = new Body(planet.readDouble(), planet.readDouble(), planet.readDouble(), planet.readDouble(), planet.readDouble(), planet.readString());
		}
		return planets;
	}

	public static void main(String[] args) {
		/** The method that can convert String to Double is from StackOverflow. */
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		Body[] bodies = NBody.readBodies(filename);
		double radius = NBody.readRadius(filename);
		StdDraw.enableDoubleBuffering();
		StdDraw.clear();
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0, 0, "images/starfield.jpg", 2*radius, 2*radius);
		StdDraw.show();
		for (int i = 0; i < bodies.length; i = i + 1) {
			bodies[i].draw();
		}
		for (double t = 0.0; t < T; t += dt) {
			double[] xForces = new double[bodies.length];
			double[] yForces = new double[bodies.length];
			for (int i = 0; i < bodies.length; i = i + 1) {
				xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}
			for (int j = 0; j < bodies.length; j = j + 1) {
				bodies[j].update(dt, xForces[j], yForces[j]);
			}
			StdDraw.enableDoubleBuffering();
			StdDraw.clear();
			StdDraw.setScale(-radius, radius);
			StdDraw.picture(0, 0, "images/starfield.jpg", 2*radius, 2*radius);
			for (int m = 0; m < bodies.length; m = m + 1) {
				bodies[m].draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
		}
		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}	
	}
}






















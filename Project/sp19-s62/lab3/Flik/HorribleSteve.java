public class HorribleSteve {
    public static void main(String [] args) throws Exception {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
            if (!Flik.isSameNumber(i, j)) {
                throw new Exception(
                        String.format("i:%d not same as j:%d ??", i, j));
<<<<<<< HEAD
                //System.out.println("Error");
                //return;
=======
>>>>>>> c4dd2becf78f8dae39f1bd33030d805ac08be62d
            }
        }
        System.out.println("i is " + i);
    }
}

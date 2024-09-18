package monopoly;

public class Termctl {
    // colors
    public static final int black = 0;
    public static final int red = 1;
    public static final int green = 2;
    public static final int yellow = 3;
    public static final int blue = 4;
    public static final int magenta = 5;
    public static final int cyan = 6;
    public static final int white = 7;
    // styles
    public static final int normal = 1 << 0;
    public static final int bold = 1 << 1;
    public static final int italic = 1 << 2;
    public static final int faint = 1 << 3;
    public static final int underline = 1 << 4;
    public static final int inverse = 1 << 5;

    public void apply(int color, int style)
    {
        System.out.print("\033["+(color+30)+"m");
        if ((style & normal)!= 0)
            System.out.print("\033[0m");
        if ((style & bold)!= 0)
            System.out.print("\033[1m");
        if ((style & italic)!= 0)
            System.out.print("\033[3m");
        if ((style & faint)!= 0)
            System.out.print("\033[2m");
        if ((style & underline)!= 0)
            System.out.print("\033[4m");
        if ((style & inverse)!= 0)
            System.out.print("\033[7m");
    }

    public void apply(int color)
    {
        System.out.print("\033["+(color+30)+"m");
    }

}

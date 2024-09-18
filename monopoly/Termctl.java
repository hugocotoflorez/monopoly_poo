package monopoly;

public class Termctl {
    // colors
    public static final int Black = 0;
    public static final int Red = 1;
    public static final int Green = 2;
    public static final int Yellow = 3;
    public static final int Blue = 4;
    public static final int Magenta = 5;
    public static final int Cyan = 6;
    public static final int White = 7;
    // styles
    public static final int Normal = 1 << 0;
    public static final int Bold = 1 << 1;
    public static final int Italic = 1 << 2;
    public static final int Faint = 1 << 3;
    public static final int Underline = 1 << 4;
    public static final int Inverse = 1 << 5;

    public void apply(int color, int style)
    {
        System.out.print("\033["+(color+30)+"m");
        if ((style & Normal)!= 0)
            System.out.print("\033[0m");
        if ((style & Bold)!= 0)
            System.out.print("\033[1m");
        if ((style & Italic)!= 0)
            System.out.print("\033[3m");
        if ((style & Faint)!= 0)
            System.out.print("\033[2m");
        if ((style & Underline)!= 0)
            System.out.print("\033[4m");
        if ((style & Inverse)!= 0)
            System.out.print("\033[7m");
    }

    public void apply(int color)
    {
        System.out.print("\033["+(color+30)+"m");
    }

}

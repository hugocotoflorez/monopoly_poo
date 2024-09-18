package monopoly;

public class Termctl {
    // colors
    public  final int Black = 0;
    public  final int Red = 1;
    public  final int Green = 2;
    public  final int Yellow = 3;
    public  final int Blue = 4;
    public  final int Magenta = 5;
    public  final int Cyan = 6;
    public  final int White = 7;
    public final int Reset = -30;
    // styles
    public  final int Normal = 1 << 0;
    public  final int Bold = 1 << 1;
    public  final int Italic = 1 << 2;
    public  final int Faint = 1 << 3;
    public  final int Underline = 1 << 4;
    public  final int Inverse = 1 << 5;

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

    public String set_color(int color)
    {
        return "\033["+(color+30)+"m";
    }

    public void reset()
    {
        System.out.println("\033[0m");
    }

}

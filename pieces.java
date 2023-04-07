public class pieces {
    private int xCor;
    private int yCor;
    private boolean king;
    private String color;

    public pieces()
    {
        xCor = 0;
        yCor = 0;
        king = false;
        color = " _ ";
    }

    public pieces( int x, int y, boolean b, String c)
    {
        xCor = x;
        yCor = y;
        king = b;
        color = c;
    }

    public int getX()
    {
        return xCor;
    }

    public int getY()
    {
        return yCor;
    }

    public boolean kingStatus()
    {
        return king;
    }

    public String getColor()
    {
        return color;
    }

    public void setX(int x)
    {
        xCor = x;
    }

    public void setY(int y)
    {
        yCor = y;
    }

    public void makeKing(boolean k)
    {
        king = k;
    }

    public void setColor(String c) { color = c; }
}

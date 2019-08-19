package website.dango.resistor;

public class BandColor {

    private double value;
    private int resNameId;
    private int resColorId;


    public BandColor( double value, int name, int resColorId ) {
        this.value = value;
        this.resNameId = name;
        this.resColorId = resColorId;
    }


    /**
     * @return the color in a R.color.id in the project
     */
    public int getResColorId() {
        return resColorId;
    }

    /**
     * @return the name of the color in a R.string.id
     */
    public int getName() {
        return resNameId;
    }

    /**
     * @return the value which is to assigned to the color
     */
    public double getValue() {
        return value;
    }

    public void setValue( double value ) {
        this.value = value;
    }

    public void setName( int name ) {
        this.resNameId = name;
    }

    public void setResColorId( int resColorId ) {
        this.resColorId = resColorId;
    }

}

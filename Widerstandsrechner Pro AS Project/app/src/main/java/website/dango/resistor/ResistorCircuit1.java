package website.dango.resistor;

abstract public class ResistorCircuit1 extends ResistorCircuit {

    private Double res1;


    public Double getRes1() {
        return res1;
    }



    public void setRes1( Double res1 ) {
        this.res1 = res1;
    }



    public ResistorCircuit1 duplicate( ResistorCircuit circuit ) {
        ResistorCircuit1 temp = new ResistorCircuit1() {
            @Override
            public Double calcTotalResistance( Double[] widerstandsPermutation ) {
                return circuit.getTotalResistance();
            }

            @Override
            public boolean isOrderImportant() {
                return circuit.isOrderImportant();
            }

            @Override
            public String getDescription() {
                return circuit.getDescription();
            }
        };
        temp.setRes1( ((ResistorCircuit1)circuit).getRes1() );
        temp.setTotalResistance( circuit.getTotalResistance() );
        return temp;
    }



}

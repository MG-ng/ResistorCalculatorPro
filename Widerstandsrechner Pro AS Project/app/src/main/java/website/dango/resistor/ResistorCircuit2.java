package website.dango.resistor;

abstract public class ResistorCircuit2 extends ResistorCircuit {

    private Double res1;
    private Double res2;


    public Double getRes1() {
        return res1;
    }

    public Double getRes2() {
        return res2;
    }


    public void setRes1( Double res1 ) {
        this.res1 = res1;
    }

    public void setRes2( Double res2 ) {
        this.res2 = res2;
    }


    public ResistorCircuit2 duplicate( ResistorCircuit circuit ) {
        ResistorCircuit2 temp = new ResistorCircuit2() {
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
        temp.setRes1( ((ResistorCircuit2) circuit).getRes1() );
        temp.setRes2( ((ResistorCircuit2) circuit).getRes2() );
        temp.setTotalResistance( circuit.getTotalResistance() );
        return temp;
    }
}

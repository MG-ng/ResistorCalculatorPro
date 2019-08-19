package website.dango.resistor;

abstract public class ResistorCircuit4 extends ResistorCircuit {

    private Double res1 = 0d, res2 = 0d, res3 = 0d, res4 = 0d;


    public Double getRes1() {
        return res1;
    }

    public Double getRes2() {
        return res2;
    }

    public Double getRes3() {
        return res3;
    }

    public Double getRes4() {
        return res4;
    }


    public void setRes1( Double res1 ) {
        this.res1 = res1;
    }

    public void setRes2( Double res2 ) {
        this.res2 = res2;
    }

    public void setRes3( Double res3 ) {
        this.res3 = res3;
    }

    public void setRes4( Double res4 ) {
        this.res4 = res4;
    }


    public ResistorCircuit4 duplicate( ResistorCircuit circuit ) {
        ResistorCircuit4 temp = new ResistorCircuit4() {
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
        temp.setRes1( ((ResistorCircuit4) circuit).getRes1() );
        temp.setRes2( ((ResistorCircuit4) circuit).getRes2() );
        temp.setRes3( ((ResistorCircuit4) circuit).getRes3() );
        temp.setRes4( ((ResistorCircuit4) circuit).getRes4() );
        temp.setTotalResistance( circuit.getTotalResistance() );
        return temp;
    }
}

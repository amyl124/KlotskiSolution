import java.util.zip.CRC32;

/**
 * Created by diling on 9/1/22.
 */
public class KlotskiState extends Object {

    private int[][] state;

    public KlotskiState(int[][] state) {
        this.state = makeStateCopy(state);
    }

    private static int[][] makeStateCopy(int[][] a) {
        int[][] b = new int[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                b[i][j] = a[i][j];
            }
        }
        return b;
    }


    @Override
    public int hashCode() {
        CRC32 crc = new CRC32();
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                crc.update(state[i][j]);
            }
        }

        return (int)crc.getValue();
    }


    @Override
    public boolean equals(Object other) {
        if (!(other instanceof KlotskiState)) {
            return false;
        }

        KlotskiState otherState = (KlotskiState)other;
        for (int i = 0; i < this.state.length; i++) {
            for (int j = 0; j < otherState.state[0].length; j++) {
                if (this.state[i][j] != otherState.state[i][j]) {
                    return false;
                }
            }
        }

        return true;

    }
}

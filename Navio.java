import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Navio {
    protected String tipo;
    protected Posicao posicaoInicial;
    protected char orientacao;
    protected int tamanho;

    public Navio(String tipo, int tamanho) {
        this.tipo = tipo;
        this.tamanho = tamanho;
    }

    public String getTipo() {
        return tipo;
    }

    public int getTamanho() {
        return tamanho;
    }


    private boolean afundado;

    public boolean foiAfundado() {
        return afundado;
    }

    public void setAfundado(boolean afundado) {
        this.afundado = afundado;
    }


}

class PortaAvioes extends Navio {
    public PortaAvioes(String tipo) {
        super(tipo, 8);
    }
}

class Destroyer extends Navio {
    public Destroyer(String tipo) {
        super(tipo, 5);
    }
}

class Submarino extends Navio {
    public Submarino(String tipo) {
        super(tipo, 4);
    }
}

class Fragata extends Navio {
    public Fragata(String tipo) {
        super(tipo, 3);
    }
}

class Bote extends Navio {
    public Bote(String tipo) {
        super(tipo, 2);
    }







}
package domain.factory;

import domain.Individuo;
import domain.IndividuoNRainhas;

import java.util.List;

public class IndividuoNRainhasFactory implements IndividuoFactory {

    private int nRainhas;

    @Override
    public Individuo getIndividuo() {
        return new IndividuoNRainhas(nRainhas);
    }

    public IndividuoNRainhasFactory(int nRainhas) {
        this.nRainhas = nRainhas;
    }
}

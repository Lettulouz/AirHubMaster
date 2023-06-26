package com.airhubmaster.airhubmaster.dto.game;

import java.util.List;

public class ListPersonnelDto {

    /**
     * Variable declaration
     */
    List<SetPersonnelWorkerDto> pilot;
    List<SetPersonnelWorkerDto> stewardessa;
    List<SetPersonnelWorkerDto> personelNaziemny;

    public ListPersonnelDto(List<SetPersonnelWorkerDto> pilot, List<SetPersonnelWorkerDto> stewardessa, List<SetPersonnelWorkerDto> personelNaziemny) {
        this.pilot = pilot;
        this.stewardessa = stewardessa;
        this.personelNaziemny = personelNaziemny;
    }

    public List<SetPersonnelWorkerDto> getPilot() {
        return pilot;
    }

    public void setPilot(List<SetPersonnelWorkerDto> pilot) {
        this.pilot = pilot;
    }

    public List<SetPersonnelWorkerDto> getStewardessa() {
        return stewardessa;
    }

    public void setStewardessa(List<SetPersonnelWorkerDto> stewardessa) {
        this.stewardessa = stewardessa;
    }

    public List<SetPersonnelWorkerDto> getPersonelNaziemny() {
        return personelNaziemny;
    }

    public void setPersonelNaziemny(List<SetPersonnelWorkerDto> personelNaziemny) {
        this.personelNaziemny = personelNaziemny;
    }
}

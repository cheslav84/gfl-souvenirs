package gfl.havryliuk.souvenirs.entities.dto;

import gfl.havryliuk.souvenirs.entities.Entity;
import gfl.havryliuk.souvenirs.entities.Souvenir;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SouvenirsGroupedByProductionYearDto implements Entity {

    private int productionYear;

    private List<Souvenir> souvenirs;

}

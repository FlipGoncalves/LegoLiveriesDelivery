package tqs.project.datamodels;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@Getter
@Setter
public class StatisticDTO {

    private int numorders;

    private int numriders;

    private int completedorders;

    private int sales;
}
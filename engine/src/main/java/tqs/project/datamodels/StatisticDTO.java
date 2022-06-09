package tqs.project.datamodels;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatisticDTO {
    @NonNull
    private int numorders;

    @NonNull
    private int numriders;

    @NonNull
    private int completedorders;

    @NonNull
    private int sales;
}
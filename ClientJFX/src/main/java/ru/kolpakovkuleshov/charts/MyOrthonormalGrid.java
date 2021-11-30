package ru.kolpakovkuleshov.charts;

import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Grid;
import org.jzy3d.plot3d.builder.Mapper;

import java.util.ArrayList;
import java.util.List;

public class MyOrthonormalGrid extends Grid {

    public MyOrthonormalGrid(Range xyrange, int xysteps){
        super(xyrange, xysteps);
    }

    public MyOrthonormalGrid(Range xrange, int xsteps, Range yrange, int ysteps){
        super(xrange, xsteps, yrange, ysteps);
    }

    @Override
    public List<Coord3d> apply(Mapper mapper) {
        double xstep = xrange.getRange() / (double)(xsteps-1);
        double ystep = yrange.getRange() / (double)(ysteps-1);

        List<Coord3d> output = new ArrayList<Coord3d>(xsteps*ysteps);

        for(int xi=0; xi<xsteps; xi++){
            for(int yi=0; yi<ysteps; yi++){
                double x = xrange.getMin() + xi * xstep;
                double y = yrange.getMin() + yi * ystep;
                output.add( new Coord3d(x, y, mapper.f(x, y) ) );
            }
        }
        return output;
    }
}
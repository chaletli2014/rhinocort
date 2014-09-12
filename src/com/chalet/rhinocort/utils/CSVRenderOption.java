package com.chalet.rhinocort.utils;

import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;

public class CSVRenderOption extends RenderOption {

    public static final String OUTPUT_FORMAT_CSV = "xls";
    
    public CSVRenderOption(){
        super();
    }
    
    public CSVRenderOption(IRenderOption options){
        super(options);
    }
}

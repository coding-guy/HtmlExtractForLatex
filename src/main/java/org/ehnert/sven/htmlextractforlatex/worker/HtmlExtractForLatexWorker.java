package org.ehnert.sven.htmlextractforlatex.worker;

import org.ehnert.sven.htmlextractforlatex.Application;
import org.ehnert.sven.htmlextractforlatex.threads.FileSearchRunner;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class HtmlExtractForLatexWorker {

    protected Set<File> htmlFilesInPathFound;
    private Application application;

    public HtmlExtractForLatexWorker(Application app)
    {
        this.application = app;
        htmlFilesInPathFound = new LinkedHashSet<File>();

        FileSearchRunner fsr = new FileSearchRunner( this );
        Thread fsrThread = new Thread( fsr );
        fsrThread.start();

        System.out.println( "worker has got "+htmlFilesInPathFound.size()+" files from runnable" );
    }

    public synchronized void addFileFound( File file ){
        htmlFilesInPathFound.add( file );
    }

    public synchronized void addFilesFound( Collection<File> files ){
        htmlFilesInPathFound.addAll( files );
    }

    public synchronized String getHtmlPath(){
        return application.getHtmlPath();
    }

    public synchronized FileFilter getFileFilter()
    {
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                if( file.getAbsolutePath().matches( application.getFileFilter() ) )
                    return true;

                return false;
            }
        };

        return filter;
    }
}

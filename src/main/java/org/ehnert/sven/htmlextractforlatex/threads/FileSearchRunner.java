package org.ehnert.sven.htmlextractforlatex.threads;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.ehnert.sven.htmlextractforlatex.worker.HtmlExtractForLatexWorker;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.SimpleFileVisitor;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class FileSearchRunner extends DirectoryWalker<File> implements Runnable {

    private HashSet<File> foundFilenames = new LinkedHashSet<File>();
    private HtmlExtractForLatexWorker worker;

    @Override
    public void run() {
        File startDirectory = new File( worker.getHtmlPath() );
        try {
            walk( startDirectory, foundFilenames);
        } catch (IOException e) {
            e.printStackTrace();
        }

        worker.addFilesFound( foundFilenames );
    }

    public FileSearchRunner(HtmlExtractForLatexWorker worker) {
        super(worker.getFileFilter(), -1);
        this.worker = worker;
    }

    @Override
    protected void handleFile(File file, int depth, Collection<File> results) throws IOException {
        System.out.println( "handle file: "+file.getAbsolutePath() );
        super.handleFile(file, depth, results);
    }

    @Override
    protected void handleEnd(Collection<File> results) throws IOException {
        System.out.println( "Finished file search! Found "+results.size()+" files" );
    }
}

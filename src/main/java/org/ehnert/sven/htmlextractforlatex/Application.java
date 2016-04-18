package org.ehnert.sven.htmlextractforlatex;

import org.ehnert.sven.htmlextractforlatex.worker.HtmlExtractForLatexWorker;

public class Application {

    private String htmlPath;

    public String getXpath() {
        return xpath;
    }

    public String getHtmlPath() {
        return htmlPath;
    }

    private String xpath;

    private String fileFilter;

    public String getFileFilter() {
        return fileFilter;
    }

    public enum EXIT_ERROR_CODE
    {
        WRONG_USAGE;

        private static int nextCode = 1;
        private int errorCode;

        EXIT_ERROR_CODE()
        {
            init();
        }

        private void init() {
            errorCode = nextCode;
            nextCode++;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }

    public Application( String[] args )
    {
        extractProgramArguments( args );
    }

    public static void main( String[] args )
    {
        if( args.length != 3 ) {
            exit( EXIT_ERROR_CODE.WRONG_USAGE );
            printUsage();
        }

        Application app = new Application( args );
        HtmlExtractForLatexWorker htmlExtractForLatexWorker = new HtmlExtractForLatexWorker( app );
    }

    public static void exit( EXIT_ERROR_CODE exitErrorCode )
    {
        System.out.println();
        System.out.println( exitErrorCode.toString() );
        System.exit( exitErrorCode.getErrorCode() );
    }

    private static void printUsage()
    {
        System.out.println( "please define PATH (--path) and XPATH (--xpath) and FILTER (--filter)" );
    }

    private void extractProgramArguments( String[] args )
    {
        for( String arg : args )
        {
            try {
                String[] split = arg.split("=");
                String name = split[0];
                String value = split[1];

                if( name.equalsIgnoreCase( "--path" ) ){
                    htmlPath = value;
                }
                if( name.equalsIgnoreCase( "--xpath" ) ){
                    xpath = value;
                }
                if( name.equalsIgnoreCase( "--filter" ) ){
                    fileFilter = value;
                }
            } catch( ArrayIndexOutOfBoundsException x ) {
                printUsage();
            }
        }
    }
}

package com.bixolabs.cascading;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO KKr - check out new code in trunk - uses Velocity template engine...hmmm

//import org.apache.avro.Schema;
//import org.apache.avro.tool.Tool;

public class DatumCompiler {
//    public static final String BASE_DATUM_CLASSNAME = "BaseDatum";
//
//    private final Set<Schema> _queue = new HashSet<Schema>();
//
//    /*
//     * List of Java reserved words from
//     * http://java.sun.com/docs/books/jls/third_edition/html/lexical.html.
//     */
//    private static final Set<String> RESERVED_WORDS = new HashSet<String>(Arrays.asList(new String[] { "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
//                    "continue", "default", "do", "double", "else", "enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
//                    "interface", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
//                    "throw", "throws", "transient", "true", "try", "void", "volatile", "while" }));
//    
//    private static final String FILE_HEADER = "/**\n" 
//        + " * Autogenerated by Avro\n" 
//        + " * \n" 
//        + " * DO NOT EDIT DIRECTLY\n"
//        + " * SUB-CLASS TO CUSTOMIZE\n" 
//        + " */\n";
//
//    public DatumCompiler(Schema schema) {
//        enqueue(schema);
//    }
//
//    /**
//     * Captures output file path and contents.
//     */
//    static class OutputFile {
//        String path;
//        String contents;
//
//        /**
//         * Writes output to path destination directory when it is newer than
//         * src, creating directories as necessary. Returns the created file.
//         */
//        File writeToDestination(File src, File destDir) throws IOException {
//            File f = new File(destDir, path);
//            if (src != null && f.exists() && f.lastModified() >= src.lastModified())
//                return f; // already up to date: ignore
//            f.getParentFile().mkdirs();
//            FileWriter fw = new FileWriter(f);
//            try {
//                fw.write(FILE_HEADER);
//                fw.write(contents);
//            } finally {
//                fw.close();
//            }
//            return f;
//        }
//    }
//
//    /** Generates Java classes for a schema. */
//    public static void compileSchema(String superDatum, File src, File dest) throws IOException {
//        Schema schema = Schema.parse(src);
//        if (schema.isError()) {
//            throw new RuntimeException("Error schemas are not supported: " + schema);
//        }
//
//        DatumCompiler compiler = new DatumCompiler(schema);
//        compiler.compileToDestination(superDatum, src, dest);
//    }
//
//    public static String makeFieldNameConstant(String fieldName) {
//        StringBuilder result = new StringBuilder();
//        
//        boolean inUpper = false;
//        boolean inAcronym = false;
//        
//        for (char c : fieldName.toCharArray()) {
//            boolean isUpper = Character.isUpperCase(c);
//            if (isUpper != inUpper) {
//                if (isUpper || (inUpper && inAcronym)) {
//                    result.append('_');
//                }
//                
//                inAcronym = false;
//            } else if (isUpper) {
//                inAcronym = true;
//            }
//            
//            inUpper = isUpper;
//            result.append(Character.toUpperCase(c));
//        }
//        
//        result.append("_FN");
//        return result.toString();
//    }
//    
//    static String mangle(String word) {
//        if (RESERVED_WORDS.contains(word)) {
//            return word + "$";
//        }
//        return word;
//    }
//
//    /** Recursively enqueue schemas that need a class generated. */
//    private void enqueue(Schema schema) {
//        if (_queue.contains(schema))
//            return;
//        
//        switch (schema.getType()) {
//            case RECORD:
//                _queue.add(schema);
//                for (Schema.Field field : schema.getFields()) {
//                    enqueue(field.schema());
//                }
//                break;
//                
//            case MAP:
//                enqueue(schema.getValueType());
//                break;
//                
//            case ARRAY:
//                enqueue(schema.getElementType());
//                break;
//                
//            case ENUM:
//                _queue.add(schema);
//                break;
//                
//            case STRING:
//            case BYTES:
//            case INT:
//            case LONG:
//            case FLOAT:
//            case DOUBLE:
//            case BOOLEAN:
//                break;
//                
//            case FIXED:
//            case UNION:
//            case NULL:
//                throw new RuntimeException("Unsupported type: " + schema);
//
//            default:
//                throw new RuntimeException("Unknown type: " + schema);
//        }
//}
//
//    private void compileToDestination(String superDatum, File src, File dst) throws IOException {
//        for (Schema schema : _queue) {
//            OutputFile o = compile(superDatum, schema);
//            o.writeToDestination(src, dst);
//        }
//    }
//
//    private static String makePath(String name, String space) {
//        if (space == null || space.isEmpty()) {
//            return name + ".java";
//        } else {
//            return space.replace('.', File.separatorChar) + File.separatorChar + name + ".java";
//        }
//    }
//
//    private void header(StringBuilder out, String namespace) {
//        if (namespace != null) {
//            line(out, 0, "package " + namespace + ";\n");
//        }
//        
//        line(out, 0, "import cascading.tuple.Fields;");
//        line(out, 0, "import cascading.tuple.TupleEntry;");
//        line(out, 0, "import bixo.cascading.BaseDatum;");
//        line(out, 0, "");
//        line(out, 0, "@SuppressWarnings(\"serial\")");
//    }
//
//    private OutputFile compile(String superDatum, Schema schema) {
//        OutputFile outputFile = new OutputFile();
//        String name = mangle(schema.getName());
//        outputFile.path = makePath(name, schema.getNamespace());
//        StringBuilder out = new StringBuilder();
//        header(out, schema.getNamespace());
//        
//        switch (schema.getType()) {
//            case RECORD:
//                doc(out, 0, schema.getDoc());
//                line(out, 0, String.format("public class %s extends %s {", name, superDatum));
//                
//                // field declarations
//                StringBuilder fieldNames = new StringBuilder();
//                for (Schema.Field field : schema.getFields()) {
//                    doc(out, 1, field.doc());
//                    String fieldName = makeFieldNameConstant(field.name());
//                    line(out, 1, String.format("public static final String %s = fieldName(%s.class, \"%s\");",
//                                    fieldName,
//                                    name,
//                                    field.name()));
//                    if (fieldNames.length() > 0) {
//                        fieldNames.append(", ");
//                    }
//                    fieldNames.append(fieldName);
//                }
//                
//                line(out, 0, "");
//
//                String fieldDeclaration;
//                if (isBaseDatum(superDatum)) {
//                    fieldDeclaration = String.format("new Fields(%s)", fieldNames.toString());
//                } else {
//                    fieldDeclaration = String.format("%s.FIELDS.append(new Fields(%s))", superDatum, fieldNames.toString());
//                }
//                line(out, 1, String.format("public static final Fields FIELDS = %s;", fieldDeclaration));
//                line(out, 0, "");
//
//                // Create constructors. If we have a non-BaseDatum superclass, we need to add special constructors.
//                if (isBaseDatum(superDatum)) {
//                    line(out, 1, String.format("public %s(Fields fields) {", name));
//                    line(out, 2, "super(fields);");
//                    line(out, 1, "}");
//                    line(out, 0, "");
//                    
//                    line(out, 1, String.format("public %s(Fields fields, Tuple tuple) {", name));
//                    line(out, 2, "super(fields, tuple);");
//                    line(out, 1, "}");
//                    line(out, 0, "");
//                }
//                
//                // Now do regular constructors
//                line(out, 1, String.format("public %s(Tuple tuple) {", name));
//                line(out, 2, "super(tuple);");
//                line(out, 1, "}");
//                line(out, 0, "");
//                
//                line(out, 1, String.format("public %s(TupleEntry te) {", name));
//                line(out, 2, "super(FIELDS);");
//                line(out, 2, "setTupleEntry(te);");
//                line(out, 1, "}");
//                line(out, 0, "");
//                
//                // TODO KKr - create constructor that takes all of the individual fields,
//                // and calls the set<fieldname> methods to set their values.
//                StringBuilder paramList = new StringBuilder();
//                for (Schema.Field field : schema.getFields()) {
//                    if (paramList.length() > 0) {
//                        paramList.append(", ");
//                    }
//                    
//                    paramList.append(unbox(field.schema()));
//                    paramList.append(' ');
//                    paramList.append(field.name());
//                }
//                
//                line(out, 1, String.format("public %s(%s) {", name, paramList.toString()));
//                line(out, 2, "super(FIELDS);");
//                line(out, 2, "setParam(param);");
//                line(out, 1, "}");
//                line(out, 0, "");
//                
//                // TODO KKr - create getters and setters for each of the individual fields.
//                // For maps and arrays, do special processing.
//                
////                public void setPhrase(String phrase) {
////                    _tupleEntry.set(PHRASE_FN, phrase);
////                }
////                
////                public String getPhrase() {
////                    return _tupleEntry.getString(PHRASE_FN);
////                }
//                
//                line(out, 0, "}");
//                break;
//                
//            case ENUM:
//                doc(out, 0, schema.getDoc());
//                line(out, 0, "public enum " + name + " { ");
//                StringBuilder b = new StringBuilder();
//                int count = 0;
//                for (String symbol : schema.getEnumSymbols()) {
//                    b.append(mangle(symbol));
//                    if (++count < schema.getEnumSymbols().size())
//                        b.append(", ");
//                }
//                line(out, 1, b.toString());
//                line(out, 0, "}");
//                break;
//                
//            case MAP:
//            case ARRAY:
//            case STRING:
//            case BYTES:
//            case INT:
//            case LONG:
//            case FLOAT:
//            case DOUBLE:
//            case BOOLEAN:
//                break;
//                
//            case NULL:      // TODO KKr - do we support the null type?
//            case UNION:
//            case FIXED:
//                throw new RuntimeException("Unsupported type: " + schema);
//                
//            default:
//                throw new RuntimeException("Unknown type: " + schema);
//        }
//
//    outputFile.contents = out.toString();
//    return outputFile;
//}
//
//    private boolean isBaseDatum(String superDatum) {
//        return superDatum.equals(BASE_DATUM_CLASSNAME);
//    }
//
//    private void doc(StringBuilder out, int indent, String doc) {
//        if (doc != null) {
//            line(out, indent, "/** " + escapeForJavaDoc(doc) + " */");
//        }
//    }
//
//    /**
//     * Be sure that generated code will compile by replacing end-comment markers
//     * with the appropriate HTML entity.
//     */
//    private String escapeForJavaDoc(String doc) {
//        return doc.replace("*/", "*&#47;");
//    }
//
//    private String type(Schema schema) {
//        switch (schema.getType()) {
//            case ENUM:
//                return mangle(schema.getFullName());
//            case ARRAY:
//                return "java.util.List<" + type(schema.getElementType()) + ">";
//            case MAP:
//                return "java.util.Map<String, " + type(schema.getValueType()) + ">";
//            case STRING:
//                return "String";
//            case BYTES:
//                return "java.nio.ByteBuffer";
//            case INT:
//                return "int";
//            case LONG:
//                return "long";
//            case FLOAT:
//                return "float";
//            case DOUBLE:
//                return "double";
//            case BOOLEAN:
//                return "boolean";
//                
//            case FIXED:
//            case UNION:
//            case NULL:
//                throw new RuntimeException("Unsupported type type: " + schema);
//
//            case RECORD:
//                throw new RuntimeException("Can't have records inside of records: " + schema);
//
//            default:
//                throw new RuntimeException("Unknown type: " + schema);
//        }
//    }
//
//    private String unbox(Schema schema) {
//        switch (schema.getType()) {
//            case INT:
//            return "int";
//            case LONG:
//            return "long";
//            case FLOAT:
//            return "float";
//            case DOUBLE:
//            return "double";
//            case BOOLEAN:
//            return "boolean";
//            default:
//            return type(schema);
//        }
//    }
//
//    private void line(StringBuilder out, int indent, String text) {
//        for (int i = 0; i < indent; i++) {
//            out.append("    ");
//        }
//        out.append(text);
//        out.append("\n");
//    }
//
//    static String esc(Object o) {
//        return o.toString().replace("\"", "\\\"");
//    }
//
//    /**
//     * Implementation of Tool for inclusion by the "avro-tools" runner.
//     */
//    public static class SpecificCompilerTool implements Tool {
//        @Override
//        public int run(InputStream in, PrintStream out, PrintStream err, List<String> args) throws Exception {
//            String superDatum;
//            int argIndex = 0;
//            
//            if (args.size() == 2) {
//                superDatum = BASE_DATUM_CLASSNAME;
//            } else if (args.size() == 3) {
//                superDatum = args.get(argIndex++);
//            } else {
//                System.err.println("Expected 2 or 3 arguments: [superDatum] inputfile outputdir");
//                return 1;
//            }
//            
//            File input = new File(args.get(argIndex++));
//            File output = new File(args.get(argIndex++));
//            compileSchema(superDatum, input, output);
//            return 0;
//        }
//
//        @Override
//        public String getName() {
//            return "compile";
//        }
//
//        @Override
//        public String getShortDescription() {
//            return "Generates Java code for Cascading Datums, based on the given schema.";
//        }
//    }

}
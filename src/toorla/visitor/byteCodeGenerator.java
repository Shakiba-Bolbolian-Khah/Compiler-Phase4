package toorla.visitor;


import toorla.ast.Program;
import toorla.ast.declaration.classDecs.ClassDeclaration;
import toorla.ast.declaration.classDecs.EntryClassDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.ClassMemberDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.FieldDeclaration;
import toorla.ast.declaration.classDecs.classMembersDecs.MethodDeclaration;
import toorla.ast.declaration.localVarDecs.ParameterDeclaration;
import toorla.ast.expression.*;
import toorla.ast.expression.binaryExpression.*;
import toorla.ast.expression.unaryExpression.Neg;
import toorla.ast.expression.unaryExpression.Not;
import toorla.ast.expression.value.BoolValue;
import toorla.ast.expression.value.IntValue;
import toorla.ast.expression.value.StringValue;
import toorla.ast.statement.*;
import toorla.ast.statement.localVarStats.LocalVarDef;
import toorla.ast.statement.localVarStats.LocalVarsDefinitions;
import toorla.ast.statement.returnStatement.Return;
import toorla.symbolTable.SymbolTable;
import toorla.symbolTable.exceptions.ItemNotFoundException;
import toorla.symbolTable.symbolTableItem.ClassSymbolTableItem;
import toorla.symbolTable.symbolTableItem.MethodSymbolTableItem;
import toorla.symbolTable.symbolTableItem.SymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.FieldSymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.LocalVariableSymbolTableItem;
import toorla.symbolTable.symbolTableItem.varItems.VarSymbolTableItem;
import toorla.typeChecker.ExpressionTypeExtractor;
import toorla.typeChecker.TypeChecker;
import toorla.types.Type;
import toorla.utilities.stack.Stack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class byteCodeGenerator implements IVisitor<Void> {
    private int labelIndex;

    private boolean lhsAssign;
    private String lhsNode;
    private String initBody;
    private  Stack<String> whileLabels;
    private  Stack<String> endWhileLabels;

    private String entryClass;
    private String currentClass;

    private boolean returned;

    private int indexCount;

    private ExpressionTypeExtractor expressionTypeExtractor;

    public byteCodeGenerator(TypeChecker typeChecker){
        labelIndex = 0;
        lhsAssign = false;
        lhsNode = "";
        expressionTypeExtractor = typeChecker.getExpressionTypeExtractor();
        whileLabels = new Stack<>();
        endWhileLabels = new Stack<>();
        currentClass = "";
        initBody = "";
        returned = false;
        indexCount = 0;
    }

    private  FileWriter bufferedWriter;

    private void classFileMaker(String fileName){
        try {
            System.out.println("in fileMaker with name:"+fileName);
            bufferedWriter = new FileWriter("./artifact/"+fileName+".j");//TODO it should be in artifact
        } catch (IOException e) {
            System.out.println("there is something wrong in classFileMaker");
        }
    }

    @Override
    public Void visit(Plus plusExpr) {
        plusExpr.getLhs().accept(this);
        plusExpr.getRhs().accept(this);
        try {
            bufferedWriter.write("iadd\n");
        } catch (IOException e) {
            System.out.println("in Plus");
        }

        return null;
    }

    @Override
    public Void visit(Minus minusExpr) {
        minusExpr.getLhs().accept(this);
        minusExpr.getRhs().accept(this);
        try {
            bufferedWriter.write("isub\n");
        } catch (IOException e) {
            System.out.println("in Minus");
        }
        return null;
    }

    @Override
    public Void visit(Times timesExpr) {
        timesExpr.getLhs().accept(this);
        timesExpr.getRhs().accept(this);
        try {
            bufferedWriter.write("imul\n");
        } catch (IOException e) {
            System.out.println("in Times");
        }
        return null;
    }

    @Override
    public Void visit(Division divExpr) {
        divExpr.getLhs().accept(this);
        divExpr.getRhs().accept(this);
        try {
            bufferedWriter.write("idiv\n");
        } catch (IOException e) {
            System.out.println("in Division");
        }
        return null;
    }

    @Override
    public Void visit(Modulo moduloExpr) {
        moduloExpr.getLhs().accept(this);
        moduloExpr.getRhs().accept(this);
        try {
            bufferedWriter.write("irem\n");
        } catch (IOException e) {
            System.out.println("in Modulo");
        }
        return null;
    }

    @Override
    public Void visit(Equals equalsExpr) {



        Type lhsType = equalsExpr.getLhs().accept(expressionTypeExtractor);

        if(lhsType.toString().equals("int") || lhsType.toString().equals("bool")){
            equalsExpr.getLhs().accept(this);
            equalsExpr.getRhs().accept(this);
            try {
                bufferedWriter.write("if_icmpne EQUALLABEL"+labelIndex+"\n");
                bufferedWriter.write("iconst_1\n");
                bufferedWriter.write("goto EQUALCOMPLETE"+(labelIndex+1)+"\n");
                bufferedWriter.write("EQUALLABEL"+labelIndex+":\n");
                labelIndex++;
                bufferedWriter.write("iconst_0\n");
                bufferedWriter.write("EQUALCOMPLETE"+labelIndex+":\n");
                labelIndex++;
            } catch (IOException e) {
                System.out.println("in equals");
            }
        }
        else if(lhsType.toString().startsWith("array of")){

            equalsExpr.getLhs().accept(this);
            equalsExpr.getRhs().accept(this);
            try {
                bufferedWriter.write("invokestatic java/util/Arrays.equals("+lhsType.getCode()+lhsType.getCode()+")Z\n" );
            } catch (IOException e) {
                System.out.println("in equals");
            }
        }
        else{
            equalsExpr.getLhs().accept(this);
            equalsExpr.getRhs().accept(this);
            try {
                if(lhsType.toString().equals("string")){
                    bufferedWriter.write("invokevirtual "+"java/lang/String.equals"+"(Ljava/lang/Object;)Z\n");
                }
                else{
                    bufferedWriter.write("invokevirtual "+"java/lang/Object.equals"+"(Ljava/lang/Object;)Z\n");
                }
            } catch (IOException e) {
                System.out.println("in equals");
            }
        }

        return null;
    }

    @Override
    public Void visit(GreaterThan gtExpr) {
        gtExpr.getLhs().accept(this);
        gtExpr.getRhs().accept(this);

        try {
            bufferedWriter.write("if_icmpgt GTLABEL" + labelIndex + "\n");
            bufferedWriter.write("iconst_0\n");
            bufferedWriter.write("goto GTCOMPLETE"+(labelIndex+1)+"\n");
            bufferedWriter.write("GTLABEL"+labelIndex+":\n");
            labelIndex++;
            bufferedWriter.write("iconst_1\n");
            bufferedWriter.write("GTCOMPLETE"+labelIndex+":\n");
            labelIndex++;
        } catch (IOException e) {
            System.out.println("in GreaterThan");
        }


        return null;
    }

    @Override
    public Void visit(LessThan lessThanExpr) {
        lessThanExpr.getLhs().accept(this);
        lessThanExpr.getRhs().accept(this);

        try {
            bufferedWriter.write("if_icmplt LTLABEL" + labelIndex+"\n");
            bufferedWriter.write("iconst_0\n");
            bufferedWriter.write("goto LTCOMPLETE"+(labelIndex+1)+ "\n");
            bufferedWriter.write("LTLABEL"+labelIndex+":\n");
            labelIndex++;
            bufferedWriter.write("iconst_1\n");
            bufferedWriter.write("LTCOMPLETE"+labelIndex+":\n");
            labelIndex++;
        } catch (IOException e) {
            System.out.println("in LessThan");
        }

        return null;
    }

    @Override
    public Void visit(And andExpr) {
        try {
            andExpr.getLhs().accept(this);
            bufferedWriter.write("ifeq ANDLABEL"+labelIndex+"\n");//TODO check ifeq
            int endLabel = labelIndex;
            labelIndex++;
            andExpr.getRhs().accept(this);
            bufferedWriter.write("ifeq ANDLABEL"+endLabel+"\n");
            bufferedWriter.write("iconst_1\n");
            bufferedWriter.write("goto ANDCOMPLETE"+(labelIndex)+"\n");
            bufferedWriter.write("ANDLABEL"+endLabel+":\n");
            bufferedWriter.write("iconst_0\n");
            bufferedWriter.write("ANDCOMPLETE"+labelIndex+":\n");
            labelIndex++;
        } catch (IOException e) {
            System.out.println("in And");
        }
        return null;
    }

    @Override
    public Void visit(Or orExpr) {
        try {
            orExpr.getLhs().accept(this);
            bufferedWriter.write("ifne ORLABEL"+labelIndex+"\n");//TODO check ifeq
            int endLabel = labelIndex;
            labelIndex++;
            orExpr.getRhs().accept(this);
            bufferedWriter.write("ifne ORLABEL"+endLabel+"\n");
            bufferedWriter.write("iconst_0\n");
            bufferedWriter.write("goto ORCOMPLETE"+(labelIndex)+"\n");
            bufferedWriter.write("ORLABEL"+endLabel+":\n");
            bufferedWriter.write("iconst_1\n");
            bufferedWriter.write("ORCOMPLETE"+labelIndex+":\n");
            labelIndex++;
        } catch (IOException e) {
            System.out.println("in Or");
        }
        return null;
    }

    @Override
    public Void visit(Neg negExpr) {
        negExpr.getExpr().accept(this);
        try {
            bufferedWriter.write("ineg\n");
        } catch (IOException e) {
            System.out.println("in Neg");
        }
        return null;
    }

    @Override
    public Void visit(Not notExpr) {
        notExpr.getExpr().accept(this);
        try {
            bufferedWriter.write("ifne NOTLABEL"+labelIndex+"\n");
            bufferedWriter.write("iconst_1\n");
            bufferedWriter.write("goto NOTCOMPLETE"+(labelIndex+1)+"\n");
            bufferedWriter.write("NOTLABEL"+labelIndex+":\n");
            labelIndex++;
            bufferedWriter.write("iconst_0\n");
            bufferedWriter.write("NOTCOMPLETE"+labelIndex+":\n");
            labelIndex++;
        } catch (IOException e) {
            System.out.println("in Not");
        }
        return null;
    }

    @Override
    public Void visit(MethodCall methodCall) {//TODO handle return type
        System.out.println("in first of methodCall with name:"+methodCall.getMethodName().getName());
        Type instanceType = methodCall.getInstance().accept(expressionTypeExtractor);

        methodCall.getInstance().accept(this);

        String argCode = "";
        for(Expression arg:methodCall.getArgs()){
            arg.accept(this);
            Type argType = arg.accept(expressionTypeExtractor);
            argCode += (argType.getCode());
        }

        if(methodCall.getInstance().toString().equals("(Self)")){

            try {
                MethodSymbolTableItem methodSymbolTableItem =(MethodSymbolTableItem) SymbolTable.top().get("method_"+methodCall.getMethodName().getName());
                Type returnType = methodSymbolTableItem.getReturnType();
                bufferedWriter.write("invokevirtual "+currentClass+"/"+ methodCall.getMethodName().getName() + "("+argCode+")"+returnType.getCode()+"\n");
            } catch (IOException | ItemNotFoundException e) {
                System.out.println("in methodCall");
            }
        }
        else{
            try {
                ClassSymbolTableItem classSymbolTableItem = (ClassSymbolTableItem) SymbolTable.root.get("class_"+instanceType.toString());
                SymbolTable classSymbolTable = classSymbolTableItem.getSymbolTable();
                MethodSymbolTableItem methodSymbolTableItem = (MethodSymbolTableItem) classSymbolTable.get("method_"+methodCall.getMethodName().getName());
                Type returnType = methodSymbolTableItem.getReturnType();

                bufferedWriter.write("invokevirtual class_"+instanceType.toString()+"/"+methodCall.getMethodName().getName()+"("+argCode+")"+returnType.getCode()+"\n");


            } catch (ItemNotFoundException | IOException e) {
                System.out.println("in methodCall");
            }

        }
        return null;
    }

    @Override
    public Void visit(Identifier identifier) {
        boolean isLhs = lhsAssign;
        lhsAssign = false;
        System.out.println("in identifier with name:"+identifier.getName());
        try {
            int index;
            VarSymbolTableItem varSymbolTableItem =(VarSymbolTableItem) SymbolTable.top().get("var_"+identifier.getName());
            if(varSymbolTableItem.isLocalVar()){
                System.out.println("islocalvar");
                LocalVariableSymbolTableItem localVariableSymbolTableItem = (LocalVariableSymbolTableItem) varSymbolTableItem;
                index = localVariableSymbolTableItem.getIndex();
                String typeName = localVariableSymbolTableItem.getVarType().toString();
                if (!isLhs) {

                    if(typeName.equals("int") || typeName.equals("bool")){
                        bufferedWriter.write("iload "+index+"\n");
                    }
                    else{
                        bufferedWriter.write("aload "+index+"\n");
                    }
                }
                else{
                    if(typeName.equals("int") || typeName.equals("bool")){
                        lhsNode = "istore "+index;
                    }
                    else{
                        lhsNode = "astore "+index;
                    }
                }
            }
            else{
                try {
                    ClassSymbolTableItem classSymbolTableItem = (ClassSymbolTableItem) SymbolTable.root.get(currentClass);
                    SymbolTable classSymbolTable = classSymbolTableItem.getSymbolTable();
                    FieldSymbolTableItem fieldSymbolTableItem = (FieldSymbolTableItem) varSymbolTableItem;
                    Type returnType = fieldSymbolTableItem.getFieldType();
                    bufferedWriter.write("aload_0\n");
                    if(!isLhs){
                        bufferedWriter.write("getfield "+currentClass+"/"+identifier.getName()+" "+returnType.getCode());
                        bufferedWriter.write("\n");
                    }
                    else{
                        lhsNode = "putfield "+currentClass+"/"+identifier.getName()+" "+returnType.getCode();
                    }
                } catch (ItemNotFoundException | IOException e) {
                    System.out.println("Identifier fieldCall");
                }
            }


        } catch (ItemNotFoundException | IOException e) {
            System.out.println("in Identifier");
        }

        return null;
    }

    @Override
    public Void visit(Self self) {
        try {
            bufferedWriter.write("aload_0\n");
        } catch (IOException e) {
            System.out.println("In Self");
        }
        return null;
    }

    @Override
    public Void visit(IntValue intValue) {
        try {
            bufferedWriter.write("ldc " + intValue.getConstant()+"\n");
        } catch (IOException e) {
            System.out.println(" In IntValue");
        }
        return null;
    }

    @Override
    public Void visit(NewArray newArray) {
        try {
            newArray.getLength().accept(this);
            String arrayType = newArray.getType().toString();
            if( arrayType.equals("int") || arrayType.equals("bool")){
                bufferedWriter.write("newarray "+arrayType);
            }
            else if( arrayType.equals("string")){
                bufferedWriter.write("anewarray Ljava/lang/String;"); //TODO check it later
            }
            else{
                bufferedWriter.write("anewarray class_" + arrayType);
            }
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.out.println("In NewArray");
        }
        return null;
    }

    @Override
    public Void visit(BoolValue booleanValue) {
        try {
            if( booleanValue.isConstant()){
                bufferedWriter.write("iconst_1");
            }
            else{
                bufferedWriter.write("iconst_0");
            }
            bufferedWriter.write("\n");
        }
        catch (IOException e){
            System.out.println("In BoolValue");
        }
        return null;
    }

    @Override
    public Void visit(StringValue stringValue) {
        try {
            bufferedWriter.write("ldc "+stringValue.getConstant() );
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.out.println("In StringValue");
        }
        return null;
    }

    @Override
    public Void visit(NewClassInstance newClassInstance) {
        try {
            bufferedWriter.write("new class_" + newClassInstance.getClassName().getName());
            bufferedWriter.write("\n");
            bufferedWriter.write("dup");
            bufferedWriter.write("\n");
            bufferedWriter.write("invokespecial class_"+newClassInstance.getClassName().getName()+"/<init>()V");
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.out.println("In NewClassInstance");
        }
        return null;
    }

    @Override
    public Void visit(FieldCall fieldCall) {
        boolean isLhs = lhsAssign;
        lhsAssign = false;

        Type instanceType = fieldCall.getInstance().accept(expressionTypeExtractor);
        fieldCall.getInstance().accept(this);

        if(fieldCall.getField().getName().equals("length") && instanceType.toString().startsWith("array of")){
            try {
                bufferedWriter.write("arraylength\n");
                return null;
            } catch (IOException e) {
                System.out.println("in fieldCall");
            }

        }


        if(fieldCall.getInstance().toString().equals("(Self)")){
            try {
                ClassSymbolTableItem classSymbolTableItem = (ClassSymbolTableItem) SymbolTable.root.get("class_"+instanceType.toString());
                SymbolTable classSymbolTable = classSymbolTableItem.getSymbolTable();
                FieldSymbolTableItem fieldSymbolTableItem = (FieldSymbolTableItem) classSymbolTable.get("var_" + fieldCall.getField().getName());
                Type returnType = fieldSymbolTableItem.getFieldType();
                if(!isLhs){
                    bufferedWriter.write("getfield "+currentClass+"/"+fieldCall.getField().getName()+" "+returnType.getCode());
                    bufferedWriter.write("\n");
                }
                else{
                    lhsNode = "putfield "+currentClass+"/"+fieldCall.getField().getName()+" "+returnType.getCode();
                }
            } catch (ItemNotFoundException | IOException e) {
                System.out.println("fieldCall");
            }

        }
        else{
            ClassSymbolTableItem classSymbolTableItem = null;
            try {
                classSymbolTableItem = (ClassSymbolTableItem) SymbolTable.root.get("class_"+instanceType.toString());
                SymbolTable classSymbolTable = classSymbolTableItem.getSymbolTable();
                FieldSymbolTableItem fieldSymbolTableItem = (FieldSymbolTableItem) classSymbolTable.get("var_" + fieldCall.getField().getName());
                Type returnType = fieldSymbolTableItem.getFieldType();
                if(!isLhs){
                    bufferedWriter.write("getfield class_"+instanceType.toString()+"/"+ fieldCall.getField().getName()+" "+returnType.getCode());
                    bufferedWriter.write("\n");
                }
                else{
                    lhsNode = "putfield class_"+instanceType.toString()+"/"+ fieldCall.getField().getName()+" "+returnType.getCode();
                }
            } catch (ItemNotFoundException | IOException e) {
                System.out.println("fieldCall");
            }
        }

        return null;
    }

    @Override
    public Void visit(ArrayCall arrayCall) {
        boolean isLhs = lhsAssign;
        lhsAssign = false;

        Type instanceType = arrayCall.getInstance().accept(expressionTypeExtractor);
        arrayCall.getInstance().accept(this);
        arrayCall.getIndex().accept(this);
        try {
            System.out.println("in arrayCall:"+instanceType.toString());
            if(instanceType.toString().equals("array of int") | instanceType.toString().equals("array of bool")) {
                if(!isLhs){
                    bufferedWriter.write("iaload");//TODO maybe we need to push iastore
                    bufferedWriter.write("\n");

                }
                else{
                    lhsNode = "iastore";
                }
            }
            else {
                if(!isLhs){
                    bufferedWriter.write("aaload");
                    bufferedWriter.write("\n");

                }
                else{
                    lhsNode = "aastore";
                }
            }
        } catch (IOException e) {
            System.out.println("In ArrayCall");
        }


        return null;
    }

    @Override
    public Void visit(NotEquals notEquals) {

        Type lhsType = notEquals.getLhs().accept(expressionTypeExtractor);

        if(lhsType.toString().equals("int") || lhsType.toString().equals("bool")){
            notEquals.getLhs().accept(this);
            notEquals.getRhs().accept(this);
            try {
                bufferedWriter.write("if_icmpne EQUALLABEL"+labelIndex);
                bufferedWriter.write("\n");
                bufferedWriter.write("iconst_1");
                bufferedWriter.write("\n");
                bufferedWriter.write("goto EQUALCOMPLETE"+(labelIndex+1));
                bufferedWriter.write("\n");
                bufferedWriter.write("EQUALLABEL"+labelIndex+":");
                labelIndex++;
                bufferedWriter.write("\n");
                bufferedWriter.write("iconst_0");
                bufferedWriter.write("\n");
                bufferedWriter.write("EQUALCOMPLETE"+labelIndex+":");
                labelIndex++;
                bufferedWriter.write("\n");
            } catch (IOException e) {
                System.out.println("in equals");
            }
        }
        else if(lhsType.toString().startsWith("array of")){

            notEquals.getLhs().accept(this);
            notEquals.getRhs().accept(this);
            try {
                bufferedWriter.write("invokestatic java/util/Arrays.equals("+lhsType.getCode()+lhsType.getCode()+")Z" );
                bufferedWriter.write("\n");
            } catch (IOException e) {
                System.out.println("in equals");
            }
        }
        else{
            notEquals.getLhs().accept(this);
            notEquals.getRhs().accept(this);
            try {
                if(lhsType.toString().equals("string")){
                    bufferedWriter.write("invokevirtual "+"java/lang/String.equals"+"(Ljava/lang/Object;)Z");
                }
                else{
                    bufferedWriter.write("invokevirtual "+"java/lang/Object.equals"+"(Ljava/lang/Object;)Z");
                }
                bufferedWriter.write("\n");
            } catch (IOException e) {
                System.out.println("in notEquals");
            }
        }

        try {
            bufferedWriter.write("ifeq NEQUALLABEL"+labelIndex);
            bufferedWriter.write("\n");
            bufferedWriter.write("iconst_0");
            bufferedWriter.write("\n");
            bufferedWriter.write("goto NEQUALCOMPLETE"+(labelIndex+1));
            bufferedWriter.write("\n");
            bufferedWriter.write("NEQUALLABEL"+labelIndex+":");
            labelIndex++;
            bufferedWriter.write("\n");
            bufferedWriter.write("iconst_1");
            bufferedWriter.write("\n");
            bufferedWriter.write("NEQUALCOMPLETE"+labelIndex+":");
            labelIndex++;
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.out.println("in notEqual");
        }

        return null;
    }

    @Override
    public Void visit(PrintLine printStat) {
        try {
            Type printType = printStat.getArg().accept(expressionTypeExtractor);

            bufferedWriter.write("getstatic java/lang/System/out Ljava/io/PrintStream;");
            bufferedWriter.write("\n");
            printStat.getArg().accept(this);
            bufferedWriter.write("invokevirtual java/io/PrintStream/println("+ printType.getCode() +")V");
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.out.println("In Print!!!");
        }
        return null;
    }

    @Override
    public Void visit(Assign assignStat) {
        System.out.println("in assignStat");
        lhsAssign = true;
        assignStat.getLvalue().accept(this);
        lhsAssign = false;

        assignStat.getRvalue().accept(this);

        try {
            System.out.println("lhsNode is:"+lhsNode);
            bufferedWriter.write(lhsNode);
            bufferedWriter.write("\n");
            lhsNode = "";
        } catch (IOException e) {
            System.out.println("assign");
        }


        return null;
    }

    @Override
    public Void visit(Block block) {

        SymbolTable.pushFromQueue();
        for(Statement stmt : block.body)
            stmt.accept(this);
        SymbolTable.pop();

        return null;
    }

    @Override
    public Void visit(Conditional conditional) {
        conditional.getCondition().accept(this);
        try {
            bufferedWriter.write("ifeq ELSELABEL"+labelIndex);
            int elseIndex = labelIndex;
            labelIndex++;
            bufferedWriter.write("\n");
            SymbolTable.pushFromQueue();
            returned = false;
            conditional.getThenStatement().accept(this);
            SymbolTable.pop();
            if(!returned)
                bufferedWriter.write("goto CONDITIONCOMPLETE"+(labelIndex));
            bufferedWriter.write("\n");
            bufferedWriter.write("ELSELABEL"+elseIndex+":");
            int endLabel = labelIndex;
            bufferedWriter.write("\n");
            SymbolTable.pushFromQueue();
            conditional.getElseStatement().accept(this);
            SymbolTable.pop();
            bufferedWriter.write("CONDITIONCOMPLETE"+ endLabel+":");
            labelIndex++;
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.out.println("condition");
        }

        return null;
    }

    @Override
    public Void visit(While whileStat) {
        String whileLabel = "WHILELABEL" + labelIndex;
        String endWhileLabel = "ENDWHILELABEL" + (labelIndex+1);

        labelIndex = labelIndex + 2;

        whileLabels.push(whileLabel);
        endWhileLabels.push(endWhileLabel);

        try {
            bufferedWriter.write(whileLabel+":");
            bufferedWriter.write("\n");
            whileStat.expr.accept(this);
            bufferedWriter.write("ifeq "+endWhileLabel);
            bufferedWriter.write("\n");
            SymbolTable.pushFromQueue();
            returned = false;
            whileStat.body.accept(this);
            SymbolTable.pop();
            bufferedWriter.write("goto "+whileLabel);//TODO check this is true or not
            bufferedWriter.write("\n");
            bufferedWriter.write(endWhileLabel+":");
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.out.println("while");
        }
        whileLabels.pop();
        endWhileLabels.pop();

        return null;
    }

    @Override
    public Void visit(Return returnStat) {
        returned = true;
        System.out.println("in first of return");
        Type returnType = returnStat.getReturnedExpr().accept(expressionTypeExtractor);
        System.out.println("after accept in return");
        returnStat.getReturnedExpr().accept(this);
        try {
            if(returnType.toString().equals("int") || returnType.toString().equals("bool"))
            {
                bufferedWriter.write("ireturn");
            }
            else{
                bufferedWriter.write("areturn");
            }
            bufferedWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Void visit(Break breakStat) {
        try {
            String label = endWhileLabels.pop();
            whileLabels.push(label);
            bufferedWriter.write("goto "+label);
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.out.println("while");
        }
        return null;
    }

    @Override
    public Void visit(Continue continueStat) {
        try {
            String label = whileLabels.pop();
            whileLabels.push(label);
            bufferedWriter.write("goto "+label);
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.out.println("continue");
        }
        return null;
    }

    @Override
    public Void visit(Skip skip) {
        return null;
    }

    @Override
    public Void visit(LocalVarDef localVarDef) {
        indexCount++;
        SymbolTable.setMustBeUsedAfterDefCount(indexCount);
        System.out.println("indexCount in localVarDef is "+indexCount);
        lhsAssign = true;
        localVarDef.getLocalVarName().accept(this);
        lhsAssign = false;

        localVarDef.getInitialValue().accept(this);

        try {
            System.out.println("lhsNode is:"+lhsNode);
            bufferedWriter.write(lhsNode);
            bufferedWriter.write("\n");
            lhsNode = "";
        } catch (IOException e) {
            System.out.println("localVarDef");
        }
        return null;
    }

    @Override
    public Void visit(IncStatement incStatement) {
        lhsAssign = true;
        incStatement.getOperand().accept(this);
        lhsAssign = false;

        incStatement.getOperand().accept(this);

        try {
            bufferedWriter.write("iconst_1");
            bufferedWriter.write("\n");
            bufferedWriter.write("iadd");
            bufferedWriter.write("\n");
            bufferedWriter.write(lhsNode);
            bufferedWriter.write("\n");
            lhsNode = "";

        } catch (IOException e) {
            System.out.println("increament");
        }

        return null;
    }

    @Override
    public Void visit(DecStatement decStatement) {
        lhsAssign = true;
        decStatement.getOperand().accept(this);
        lhsAssign = false;

        decStatement.getOperand().accept(this);

        try {
            bufferedWriter.write("iconst_1");
            bufferedWriter.write("\n");
            bufferedWriter.write("isub");
            bufferedWriter.write("\n");
            bufferedWriter.write(lhsNode);
            bufferedWriter.write("\n");
            lhsNode = "";

        } catch (IOException e) {
            System.out.println("decreament");
        }
        return null;
    }

    @Override
    public Void visit(ClassDeclaration classDeclaration) {
        SymbolTable.pushFromQueue();

        String className = "class_"+classDeclaration.getName().getName();
        currentClass = className;
        expressionTypeExtractor.setCurrentClass(classDeclaration);
        String parentName;
        if(classDeclaration.getParentName().getName() == null)
            parentName = "java/lang/Object";
        else
            parentName = "class_"+ classDeclaration.getParentName().getName();

        List<ClassMemberDeclaration> methods = new ArrayList<>();

        try {
            classFileMaker(className);
            bufferedWriter.write(".class public "+ className);
            bufferedWriter.write("\n");
            bufferedWriter.write(".super "+parentName);
            bufferedWriter.write("\n");

            for(ClassMemberDeclaration mem:classDeclaration.getClassMembers()) {
                if (mem.toString().equals("MethodDeclaration"))
                    methods.add(mem);
                else
                    mem.accept(this);
            }
            bufferedWriter.write(".method public <init>()V");
            bufferedWriter.write("\n");
            bufferedWriter.write("aload_0");
            bufferedWriter.write("\n");
            bufferedWriter.write("invokespecial "+parentName+"/<init>()V");
            bufferedWriter.write("\n");
            bufferedWriter.write(initBody);
            bufferedWriter.write("return");
            bufferedWriter.write("\n");
            bufferedWriter.write(".end method");
            bufferedWriter.write("\n");
            initBody = "";
            for(ClassMemberDeclaration method:methods){
                method.accept(this);
            }

        } catch (IOException e) {
            System.out.println("classDeclaration");
        }


        try {
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("classDeclaration");
        }
        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(EntryClassDeclaration entryClassDeclaration) {
        SymbolTable.pushFromQueue();

        String className = "class_"+entryClassDeclaration.getName().getName();
        currentClass = className;
        expressionTypeExtractor.setCurrentClass(entryClassDeclaration);
        entryClass = className;
        String parentName;
        System.out.println(entryClassDeclaration.getParentName().getName());
        if(entryClassDeclaration.getParentName().getName() == null)
            parentName = "java/lang/Object";
        else
            parentName = "class_"+ entryClassDeclaration.getParentName().getName();

        List<ClassMemberDeclaration> methods = new ArrayList<>();
        try {
            classFileMaker(className);
            bufferedWriter.write(".class public "+ className);
            bufferedWriter.write("\n");
            bufferedWriter.write(".super "+parentName);
            bufferedWriter.write("\n");

            for(ClassMemberDeclaration mem:entryClassDeclaration.getClassMembers()){
                if(mem.toString().equals("MethodDeclaration"))
                    methods.add(mem);
                else
                    mem.accept(this);
            }
            System.out.println("****In Class with name: "+entryClassDeclaration.getName().getName());
            System.out.println(initBody);
            bufferedWriter.write(".method public <init>()V");
            bufferedWriter.write("\n");
            bufferedWriter.write(".limit locals 100");
            bufferedWriter.write("\n");
            bufferedWriter.write(".limit stack 1000");
            bufferedWriter.write("\n");
            bufferedWriter.write("aload_0");
            bufferedWriter.write("\n");
            bufferedWriter.write("invokespecial "+parentName+"/<init>()V");
            bufferedWriter.write("\n");
            bufferedWriter.write(initBody);
            bufferedWriter.write("return");
            bufferedWriter.write("\n");
            bufferedWriter.write(".end method");
            bufferedWriter.write("\n");
            initBody = "";
            for(ClassMemberDeclaration method:methods){
                method.accept(this);
            }

        } catch (IOException e) {
            System.out.println("entryClassDeclaration");
        }

        try {
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("in entryDeclaratio");
        }

        SymbolTable.pop();
        return null;
    }

    @Override
    public Void visit(FieldDeclaration fieldDeclaration) {
        System.out.println("in first of fieldDeclaration with name:"+fieldDeclaration.getIdentifier().getName());
        try {
            bufferedWriter.write(".field "+ fieldDeclaration.getAccessModifier().toStr() + " " + fieldDeclaration.getIdentifier().getName() + " " + fieldDeclaration.getType().getCode());
            //TODO ; in type get code
            bufferedWriter.write("\n");
            if(!fieldDeclaration.getType().getCode().startsWith("[") && !fieldDeclaration.getType().getCode().startsWith("Lclass_")) {
                initBody += "aload_0\n";
                if (fieldDeclaration.getType().getCode().equals("Ljava/lang/String;")) {
                    initBody += "ldc \"\"\n";
                } else if (fieldDeclaration.getType().getCode().equals("I") || fieldDeclaration.getType().getCode().equals("Z")) {
                    initBody += "iconst_0\n";
                }
                initBody += "putfield " + currentClass + "/" + fieldDeclaration.getIdentifier().getName() + " " + fieldDeclaration.getType().getCode() + "\n";
            }

        } catch (IOException e) {
            System.out.println("in fieldDeclaration");
        }
        return null;
    }

    @Override
    public Void visit(ParameterDeclaration parameterDeclaration) {
        return null;
    }

    @Override
    public Void visit(MethodDeclaration methodDeclaration) {
        System.out.println("in first of methodDeclaration with name:"+methodDeclaration.getName().getName());
        indexCount = 0;
        SymbolTable.setMustBeUsedAfterDefCount(indexCount);

        try {
            String accessModifier = methodDeclaration.getAccessModifier().toStr();
            String methodName = methodDeclaration.getName().getName();
            String args = "";
            String returnType = methodDeclaration.getReturnType().getCode();
            for(ParameterDeclaration arg: methodDeclaration.getArgs()){
                indexCount++;
                SymbolTable.setMustBeUsedAfterDefCount(indexCount);
                args += arg.getType().getCode();
            }
            bufferedWriter.write(".method " + accessModifier + " " + methodName + "(" + args + ")" + returnType);
            bufferedWriter.write("\n");
            bufferedWriter.write(".limit locals 100");
            bufferedWriter.write("\n");
            bufferedWriter.write(".limit stack 1000");
            bufferedWriter.write("\n");

            SymbolTable.pushFromQueue();
            for(Statement stmt:methodDeclaration.getBody()){
               // System.out.println("in for of methodDeclaration");
                stmt.accept(this);
            }

            SymbolTable.pop();
            bufferedWriter.write(".end method");
            bufferedWriter.write("\n");
        } catch (IOException e) {
            System.out.println("in methodDeclaration");
        }
        //System.out.println("end of methodDeclaration");
        return null;
    }

    @Override
    public Void visit(LocalVarsDefinitions localVarsDefinitions) {
        for(LocalVarDef lvd : localVarsDefinitions.getVarDefinitions()){
            lvd.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(Program program) {
        SymbolTable.pushFromQueue();
        boolean dir = new File("./artifact").mkdirs();
        for(ClassDeclaration classDec: program.getClasses()){
            classDec.accept(this);
        }

        try {
            classFileMaker("Runner");
            bufferedWriter.write(".class public Runner");
            bufferedWriter.write("\n");
            bufferedWriter.write(".super java/lang/Object");
            bufferedWriter.write("\n");
            bufferedWriter.write(".method public static main([Ljava/lang/String;)V");
            bufferedWriter.write("\n");
            bufferedWriter.write(".limit locals 10");
            bufferedWriter.write("\n");
            bufferedWriter.write(".limit stack 100");
            bufferedWriter.write("\n");
            bufferedWriter.write("new " + entryClass);
            bufferedWriter.write("\n");
            bufferedWriter.write("dup");
            bufferedWriter.write("\n");
            bufferedWriter.write("invokespecial "+entryClass+"/<init>()V");
            bufferedWriter.write("\n");
            bufferedWriter.write("astore_1\n");
            bufferedWriter.write("aload_1\n");
            bufferedWriter.write("invokevirtual " + entryClass + "/main()I" );
            bufferedWriter.write("\n");
            bufferedWriter.write("istore_2\n");
            bufferedWriter.write("return");
            bufferedWriter.write("\n");
            bufferedWriter.write(".end method");
            bufferedWriter.write("\n");
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("in program");
        }
        SymbolTable.pop();
        return null;
    }
}

package toorla.symbolTable.symbolTableItem.varItems;

import toorla.symbolTable.symbolTableItem.SymbolTableItem;
import toorla.types.Type;

public class VarSymbolTableItem extends SymbolTableItem {
    public static String var_modifier = "var_";
    protected Type type;
    protected boolean isLocalVar;

    public String getKey() {
        return VarSymbolTableItem.var_modifier + name;
    }

    public Type getType() {
        return type;
    }

    public boolean isLocalVar() {return  isLocalVar;}
}

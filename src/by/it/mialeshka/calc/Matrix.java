package by.it.mialeshka.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class Matrix extends Var {
    double[][] var;

    public Matrix(double[][] var) {
        this.var = var;
    }

    public Matrix(Matrix matrix) {
        this.var = matrix.var;
    }

    public Matrix(String str) {
        StringBuilder strb = new StringBuilder(str);
        strb.delete(0, 2);
        strb.delete(strb.length()-2, strb.length());
        Pattern pattern = Pattern.compile("},[ ]*\\{");
        String[] arrStr = pattern.split(strb);
        List<String[]> arrStr2 = new ArrayList<String[]>();
        for (int i = 0; i < arrStr.length; i++) {
            Pattern pattern1 = Pattern.compile(",[ ]*");
            StringBuilder strb1 = new StringBuilder(arrStr[i]);
            arrStr2.add(pattern1.split(strb1));
        }

        this.var = new double[arrStr2.size()][arrStr2.get(0).length];

        for (int i = 0; i < arrStr2.size(); i++) {
            for (int j = 0; j < arrStr2.get(0).length; j++) {
                this.var[i][j] = Double.parseDouble(arrStr2.get(i)[j]);
            }
        }
    }

    public String toString(){
        StringBuilder strb = new StringBuilder("{");

        String delimeterStr = "";
        for (int i = 0; i < var.length; i++) {
            strb.append(delimeterStr).append("{");
            String delimeter = "";
            for (int j = 0; j < var[0].length; j++) {
                strb.append(delimeter).append(var[i][j]);
                delimeter = ", ";
            }
            strb.append("}");
            delimeterStr = ", ";
        }
        strb.append("}");
        return strb.toString();
    }

    @Override
    public Var add(Var other) throws CalcException{
        if (other instanceof Scalar){
            double[][] res = new double[this.var.length][this.var[0].length];
            for (int i = 0; i < this.var.length; i++) {
                for (int j = 0; j < this.var[0].length; j++) {
                    res[i][j] = this.var[i][j] + ((Scalar) other).getVar();
                }
            }
            return new Matrix(res);
        }
        else if(other instanceof Matrix){
            if(this.var.length != ((Matrix) other).var.length || this.var[0].length != ((Matrix) other).var[0].length)
                throw new CalcException("Размеры матриц не равны");
            double[][] res = new double[this.var.length][this.var[0].length];
            for (int i = 0; i < this.var.length; i++) {
                for (int j = 0; j < this.var[0].length; j++) {
                    res[i][j] = this.var[i][j] + ((Matrix) other).var[i][j];
                }
            }
            return new Matrix(res);
        }
        else
            return super.add(other);
    }

    @Override
    public Var sub(Var other) throws CalcException{
        if (other instanceof Scalar){
            double[][] res = new double[this.var.length][this.var[0].length];
            for (int i = 0; i < this.var.length; i++) {
                for (int j = 0; j < this.var[0].length; j++) {
                    res[i][j] = this.var[i][j] - ((Scalar) other).getVar();
                }
            }
            return new Matrix(res);
        }
        else if(other instanceof Matrix){
            if(this.var.length != ((Matrix) other).var.length || this.var[0].length != ((Matrix) other).var[0].length)
                throw new CalcException("Размеры матриц не равны");
            double[][] res = new double[this.var.length][this.var[0].length];
            for (int i = 0; i < this.var.length; i++) {
                for (int j = 0; j < this.var[0].length; j++) {
                    res[i][j] = this.var[i][j] - ((Matrix) other).var[i][j];
                }
            }
            return new Matrix(res);
        }
        else
            return super.add(other);
    }

    @Override
    public Var mul(Var other) throws CalcException{
        if (other instanceof Scalar){
            double[][] res = new double[this.var.length][this.var[0].length];
            for (int i = 0; i < this.var.length; i++) {
                for (int j = 0; j < this.var[0].length; j++) {
                    res[i][j] = this.var[i][j] * ((Scalar) other).getVar();
                }
            }
            return new Matrix(res);
        }
        else if(other instanceof Vector){
            if(this.var[0].length != ((Vector) other).var.length)
                throw new CalcException("Размер вектора не соответствует матрице");
            double[] res = new double[this.var.length];
            double[] vector = ((Vector) other).getVar();
            for (int i = 0; i < this.var.length; i++) {
                for (int j = 0; j < this.var[0].length; j++) {
                    res[i] = res[i] + this.var[i][j]*vector[j];
                }
            }
            return new Vector(res);
        }
        else {
            if(this.var[0].length != ((Matrix) other).var.length)
                throw new CalcException("Размер матриц не соответствует операции *");
            double[][] res = new double[this.var.length][((Matrix) other).var.length];
            for (int i = 0; i < this.var.length; i++) {
                for (int j = 0; j < ((Matrix) other).var[0].length; j++) {
                    for (int k = 0; k < ((Matrix) other).var.length; k++) {
                        res[i][j] = res[i][j]  + this.var[i][k] *((Matrix) other).var[k][j];
                    }
                }
            }
            return new Matrix(res);
        }
    }

    @Override
    public Var div(Var other) throws CalcException{
        if (other instanceof Scalar){
            if(((Scalar) other).var == 0)
                throw new CalcException("Деление на ноль");
            double[][] res = new double[this.var.length][this.var[0].length];
            for (int i = 0; i < this.var.length; i++) {
                for (int j = 0; j < this.var[0].length; j++) {
                    res[i][j] = this.var[i][j] / ((Scalar) other).getVar();
                }
            }
            return new Matrix(res);
        }
        else
        return super.div(other);
    }

}

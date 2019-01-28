package cn.edu.bioinformatics.netepd.util;

import javaalgorithm.algorithm.Matrix;

public class AlgoNode {
	
	private int[][] adj_matrix = null;
	private double[] eigenvalues = null;
	private Matrix eigenvector = null;

	public int[][] getAdj_matrix() {
		return adj_matrix;
	}

	public void setAdj_matrix(int[][] adj_matrix) {
		this.adj_matrix = adj_matrix;
	}

	public double[] getEigenvalues() {
		return eigenvalues;
	}

	public void setEigenvalues(double[] eigenvalues) {
		this.eigenvalues = eigenvalues;
	}

	public Matrix getEigenvector() {
		return eigenvector;
	}

	public void setEigenvector(Matrix eigenvector) {
		this.eigenvector = eigenvector;
	}

}

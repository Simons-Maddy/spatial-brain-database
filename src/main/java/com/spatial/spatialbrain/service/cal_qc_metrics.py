import argparse

import scanpy as sc
import os

import sys

#new_path = '/home/user/data2/uplee/projects/spatialTransWeb/bin'
#if new_path not in sys.path:
 #   sys.path.append(new_path)

def cal_qc_metrics(args):
    adata_a1p1 = sc.read_10x_mtx(os.path.join(args.dirpath), var_names='gene_symbols', make_unique=True,
                                 cache=False, prefix=None)
    adata_a1p1.obs
    adata_a1p1.var["mt"] = adata_a1p1.var_names.str.startswith("mt-")
    sc.pp.calculate_qc_metrics(adata_a1p1, qc_vars=["mt"], inplace=True)
    adata_a1p1.obs.to_csv(args.dirpath+'adata_a1p1.obs.csv')

if __name__ == "__main__":
    descrition = 'Perform cal_qc_metrics.'
    dir = '/Users/chunfu/Desktop/BGM_lab/Spatial_Transcriptome/Intestine_Spatial/GSE158704_RAW/A1/raw_feature_bc_matrix/'
    parser = argparse.ArgumentParser(description=descrition)
    parser.add_argument('--dirpath', metavar='<ANNDATA>', default=dir, help='the dirtory path of files loaded')
    args = parser.parse_args()
    cal_qc_metrics(args)

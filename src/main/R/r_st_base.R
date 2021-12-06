# xiaocf 2021.11.25

#1.加载包，检查环境：
library(Seurat)
library(ggplot2)
library(patchwork)
library(dplyr)

#2.读取slide-seq数据的坐标文件
createslideobeject <- function(expre_path, loc_path)
{
  expre.path = expre_path
  loc.path = loc_path
  expr <- data.table::fread(
    input = expre.path,
    sep = "\t",
    data.table = FALSE
  )
  rownames(x = expr) <- expr[, 1] #添加rownames
  colnames(x = expr) <- colnames(x = expr) #读取后自带colnames，没必要进行
  #expr <- t(x = expr[, -1])
  expr <- expr[,-1] #去掉第一列row信息
  #最后expr矩阵中都为整数，没有barcode和gene的信息
  #并且rownames为rowgene，colnames为barcode，这是CreateSeuratObject的固定格式！！！
  #建立Seurat对象
  braindata <- CreateSeuratObject(
    counts = expr,
    project = 'SlideSeq',
    assay = 'Spatial'
  )
  #读取位置文件
  positions <- read.csv(
    file = loc.path
  )
  rownames(x = positions) <- positions$barcodes
  positions <- positions[, 2:3]
  #将位置文件添加到image中
  braindata[['image']] <- new(
    Class = 'SlideSeq',
    assay = "Spatial",
    coordinates = positions
  )
  #圆形过滤器，半径为2450
  braindata <- FilterSlideSeq(object = braindata, radius = 2450, do.plot = FALSE)
  return(braindata)
}
braindata <- createslideobeject("/Users/chunfu/Desktop/BGM_lab/Spatial_Transcriptome/Brain/SCP815/Puck_190921_19.digital_expression.txt"
                                ,"/Users/chunfu/Desktop/BGM_lab/Spatial_Transcriptome/Brain/SCP815/Puck_190921_19_bead_locations.csv")

#2.data preprocessing
plot1 <- VlnPlot(braindata, features = "nCount_Spatial", pt.size = 0, log = TRUE) + NoLegend()
braindata$log_nCount_Spatial <- log(braindata$nCount_Spatial)
plot2 <- SpatialFeaturePlot(braindata, features = "log_nCount_Spatial") + theme(legend.position = "right")
wrap_plots(plot1, plot2)
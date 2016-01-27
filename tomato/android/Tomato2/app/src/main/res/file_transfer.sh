icon_folders=`ls -d ic*`
from_prefix=/android/drawable-
to_prefix=mipmap-
pic_list="hdpi mdpi xhdpi xxhdpi xxxhdpi"

for folder in $icon_folders
do
    for pic_folder in $pic_list
    do
        from=$folder$from_prefix$pic_folder'/*'
        to=$to_prefix$pic_folder
        cp $from $to
    done
done
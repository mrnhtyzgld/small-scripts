import os
import glob
import time

# Kaynak dizin (alfabetik sıradaki png dosyaları buradadır)
source_dir = r"C:\Users\NihatEmreYüzügüldü\Desktop\A6"
# Hedef dizin (oluşturulma tarihine göre sıralanacak, isimleri değiştirilecek)
target_dir = r"C:\Users\NihatEmreYüzügüldü\Desktop\deneme"

# Kaynak dizindeki png dosyalarını alfabetik sırayla al
source_files = sorted(glob.glob(os.path.join(source_dir, "*.png")))

# Hedef dizindeki png dosyalarını oluşturulma tarihine göre sırala
# Burada sıralama dosya oluşturulma zamanına göre yapılacak.
# Windows'ta st_ctime yerine st_mtime veya benzeri kullanılabilir.
target_files = glob.glob(os.path.join(target_dir, "*.png"))
target_files = sorted(target_files, key=lambda x: os.stat(x).st_ctime)

# Dosya sayılarının eşleşip eşleşmediğini kontrol edin
if len(source_files) != len(target_files):
    print("Uyarı: Dosya sayıları eşit değil. İşlem gerçekleştirilmeyecek.")
    exit(1)

# Her dosyayı ilk dizindeki isimlere uygun olacak şekilde yeniden adlandır
for src_file, tgt_file in zip(source_files, target_files):
    # Kaynak dosyanın sadece adını al (yol hariç)
    new_name = os.path.basename(src_file)
    new_path = os.path.join(target_dir, new_name)

    # Aynı isimde bir dosya varsa üzerine yazmamaya dikkat edin
    # Gerekliyse bir kontrol ekleyebilirsiniz, şimdilik doğrudan yazıyor
    os.rename(tgt_file, new_path)
    print(f"'{tgt_file}' dosyası '{new_path}' olarak yeniden adlandırıldı.")

(ns flight-detector.http
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [camel-snake-kebab.core :refer :all]
            [clojure.java.shell :refer [sh]])
  (:import (java.util Date)))

(defn exec []
  (:body (client/get "https://www.koreanair.com/api/fly/revenue/from/CJU/to/GMP/on/02-05-2019-0000?flexDays=0&scheduleDriven=false&purchaseThirdPerson=&domestic=true&isUpgradeableCabin=false&adults=1&children=0&infants=0&cabinClass=ECONOMY&adultDiscounts=&adultInboundDiscounts=&childDiscounts=&childInboundDiscounts=&infantDiscounts=&infantInboundDiscounts=&_=1547297056557"
               {:headers {:Referer "https://www.koreanair.com/korea/ko/booking/dow.html",
                          :Cookie  "bm_sv=224C611B1B650E33714EC638555EA698~5VOivkW1ZYFGXLGGWY7Tze84h37t/N6dO5l3Ca2Me4dApuOM3DYaW36k/vhWiDU1cdMvWGDHSuCxzjZhmhXqHz1484ObYbiZxX0gAKExn6SBPtYWOP3zOZzShxKcwHF/dXM7ycfv68A8GNCYnToyygCosxy3zMDOdpuoUmUA0ho=; S=EAAQKm5M8pIAAzS9aS53bBDw8hCKEuwJthMrJfQhc6wAQI1p7U66YrGJORpC7KkJZ%2Fak; S_CT=EAAQJY1l0gFyioIrPqlJZ7PDa%2BuahR4XxplZ1hl5s%2FrFc6E%3D; S_RID=370BED67473A470FA7BA5FE9EBB18655; sci=nEEEH2rLmmW64rc3t6locw%3D%3D; country=kr; lang=ko; s_bghstr=100010B65DEAD72C41008005F3EB6D63D255D7625B6B36414545A094CA11B8CE7E3B6483DEF6907A6CFC82EB4A5120EB17F0DA260BF32A42A6D1BAD69E421B9A54C36E68DE656FFAECCEC14DC273E471DBD8545ABF6FB1E4BE7633D2681C503585A41586BFB898B3894678572F055A5EFD06BEC8F9E663089AB7D8E26433493767185E35EDF38C213B5E7EC877314330E23AF7DFC7795F143C9B3287858501BA103A8EEF29F13B29BBBC742C298A8069143AC60A92F753F47B1F702F96A7EFA2BD04A2B1921AA328AC751ED8F709E3F3819EEACC65ACBD36CB1AA97A113F926AB938E7931F4AEFF6A2F289D7A22EC14169E042D3927C7C16806AEA0CC6EC21E1B08E502D78A04D4367DD6B65461FF34AB8D8612705442A651AE706CDF4E3372093D1AD8BDB193E4E3C12A7C4B6E331910A81FF35F912F3893CBA9E5306CC69E43976B0F1785F3A4965D39DC201DE2880FFBA44D7C622BCBCB30100E7DCFF95266DBA1A96AE9EB1525DE6E95CFEF96F01F135C62277E64FEE3A40A68B216999CE8F87E2CE1B5C0580FED367E73DF7458CE32924E1FA55039847C72CFCB4402BA1EE053F584808E67998EB9A7B2E72BE7767097EC28564F3788F848EFA470EFD2E2AD606C31C7BA064F4E1845922F6B82868E1F467634BC544CF695754930610FFE1DA8D9C32F5D8BF6D6D79A61C6FC4AE7B3DBD3806BB4A3BE82B9BCCD5AD8C56CD3C4D; gpv_pn=https%3A%2F%2Fwww.koreanair.com%2Fkorea%2Fko%2Fbooking%2Fdow.html%23flight-select%3FparamStrgKey%3DBOOKING_GATE%26isScheduleDriven%3Dfalse%26inflowroute%3DquickBooking; s_cc=true; s_getNewRepeat=1547385609302-Repeat; s_monthinvisit=true; s_sq=%5B%5BB%5D%5D; _sdsat_member_info_ages=35~39; _sdsat_member_info_encrypt_id=1000100317A25D405AB28EDDF5ACF57DE9CE8E; _sdsat_member_info_gender=M; _sdsat_member_info_remaining_miles=100000~199999; _sdsat_member_info_skypass_tier=TB; AMCV_3131246452DDAE2D0A490D45%40AdobeOrg=1687686476%7CMCIDTS%7C17910%7CMCMID%7C44084394268334502733178363804597980763%7CMCAAMLH-1547990408%7C11%7CMCAAMB-1547990408%7CRKhpRz8krg2tLO6pguXWp5olkAcUniQYPHaMWWgdJ3xzPWQmdj0y%7CMCOPTOUT-1547392785s%7CNONE%7CMCAID%7CNONE%7CvVersion%7C3.0.0%7CMCCIDH%7C712858170; AMCVS_3131246452DDAE2D0A490D45%40AdobeOrg=1; TLTLID=EAAQ9R3tx49zLooFKJtG8Ie2IXVaHcxJ%2FzaWkpFtZPkCBwU%3D; s_vmonthnum=1548946800790%26vn%3D5; psn_adapter_s=0001i_hJEeKSthf0RE3MgWXKwln:2OAJS5DA0M; JSESSIONID=0001O2S6Nm2bCg3lVEj3mP3bC1E:1a3bfttfn; isBrowserInfo=true; TLTSID=E36CBF40173510170095862314AB265A; ak_bmsc=90A281C9A02AC3B9FFC894FA21396C433D6F3AE6AB2E0000EE3A3B5C9EB7D128~pl+cHxRZrsRcnwjsk2w3/J2pq2os8+EeFHxdkin+6gSRus43ecYN+b5Ox0occ9HvVJs4U+laPU5IxqdNa/Y5GpYe2WfS8sB765RLqdtStEdid1vt0G4zFbN7WqNqPyMEJ5SaHnFNq2nwdvIJH/Gnpmogyy1tHs8+XfW0/6hLKfTJStNAOs9c3xQjf7cA4UV5olfRx23ePjjDiqWMHmek5aC3DNPh9NDbxxkw5iSXVytnzILezzSMo+8O9aljHPwgSveibz4LMw8ICxSHklKuZUVSER1VbdwVAy8gSyfjAEGcRNm5ruk5WdOXiAr5uWnwRPLMEp4qbZ5LwiMHgoN2CHlw==; bm_sz=1984CECAFD83497EA51642DB52C27357~QAAQ5jpvPZdfSvtnAQAA3zNeR1loFz7+Bmelwm4SvgEklC40xz5wgE/7TS6CMWS4dtg9KbDUK9Ot/moirkk/v6zT6Tw6oAWvOfUtn/3V0gLncr84Td84qR0W0VCDEo7DS7tblFiGYIU0ywbWVKRELWqnq64Pyd6cK6fZ5oa024sn2zCrvFLGJtqFXEx2gyg1chk=; NULL=!DaTK2e05B+7s9eyx78NXE03/6iVeXA9MtAld6PT8ESBlUCs/SaIUdBJPwO5CMzz1Kx6UoId9oqs9dbE=; IR_PI=70ba58c8-cf34-79d6-fcb7-7fe567add70c%7C1546615463810; _abck=A8C24EBF369812DC3BF568DDEE8A60F23D6F3A2D0C7F0000481AF25B2042E623~0~tymt52QK4/6ILpmw+roqDMV1Mw7vidAOAP85FQswJfY=~-1~-1; TLTUID=8A255BDEE69A10E6037AB2426E94C88B; physicalCountry=kr"}})))

(defn filtering-flight
  []
  (let [outbound (:outbound (json/read-str (exec) :key-fn ->kebab-case-keyword))]
    (println (new Date) outbound)
    (->> outbound
         (map #(select-keys % [:departure :remaining-seats-by-booking-class]))
         (filter #(not (empty? (:remaining-seats-by-booking-class %))))
         (filter #(pos? (compare (:departure %) "2019-02-05T12")))
         (filter #(>= (+ (get-in % [:remaining-seats-by-booking-class :economyy] 0)
                         (get-in % [:remaining-seats-by-booking-class :prestigec] 0)) 3)))))

(defn detect-flight
  []
  (if (not (empty? (filtering-flight)))
   (sh "osascript" "-e" "display notification \"떳다!\" with title \"Alert!\"")))

#_(while true
    (do (detect-flight)
        (Thread/sleep 3000)))
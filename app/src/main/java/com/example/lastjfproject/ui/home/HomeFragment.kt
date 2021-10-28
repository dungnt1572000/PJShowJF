package com.example.lastjfproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lastjfproject.MainActivityViewModel
import com.example.lastjfproject.R
import com.example.lastjfproject.adapter.catagoryAdapter
import com.example.lastjfproject.databinding.HomeFragmentBinding
import com.example.lastjfproject.myInterface.Item_catagory_clickListener
import com.example.lastjfproject.myObject.Catagory
import com.example.lastjfproject.myObject.CurrentUser
import com.example.lastjfproject.storageData.DatalocalSharePrefManager
import com.example.lastjfproject.ui.catagorydetail.CatagoryDetailFragment
import com.example.lastjfproject.ui.comentFragment.CommentFragment


class HomeFragment : Fragment(), Item_catagory_clickListener {

    private lateinit var viewModel: HomeViewModel

    private lateinit var viewModelActivity: MainActivityViewModel

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        Log.e("Hello", " This is Home Fragment")

        val adapter = catagoryAdapter(getmycatagoryList(), this)
        binding.homeRecycleView.adapter = adapter


        return binding.root
    }

    private fun getmycatagoryList(): ArrayList<Catagory> {
        var myCatagoryList: ArrayList<Catagory> = ArrayList()
        myCatagoryList.add(Catagory("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBQVFBgVFBcZGBgaGR8dHBsaHBsbGxsbHBsfGxkbHRsbIS0kIh8qHxsaJTcmKi4xNDQ0HCM6PzoyPi0zNDEBCwsLEA8QHxISHTMqJCozMzEzMzMzNTwzMzMzNjMzMzMzMzMxMzM1MzUzMzMzMTMzMzMzMzMzMzMxMzMzMzMzM//AABEIAKgBLAMBIgACEQEDEQH/xAAbAAEAAgMBAQAAAAAAAAAAAAAAAwUCBAYBB//EAEAQAAIBAwIEAwYDBgQEBwAAAAECEQADIRIxBEFRYQUicQYTMoGRoUJisSNScoLB8AcUktFT0uHxFTNjc5Oiwv/EABgBAQEBAQEAAAAAAAAAAAAAAAABAgME/8QAJxEAAgICAgECBgMAAAAAAAAAAAECEQMhEjFBIlETYXGBkaEEMvH/2gAMAwEAAhEDEQA/APoVKUqGhSlKAUpSgFKUoBSlKAUpSgFKUoBSlKAUpSgFKUoBSlKAUpSgFKUoBSlKAUpSgFKUoBSlKAUpSgFKUoBSlKAUpSgFKUoBSlKAUpSgFKUoBSlKAUpSgFKUoBSlKAUpSgFKUoBXqrNeVnZInOMj7yB94qrsjdIwpXrrBIryoBSlKFFKUoBSlKAUpSgFKUoBSlKAUpSgFKUoBSlKAUpSgFKUoBSlKAUpSgFKUoCO7dCiTMdQCY9Y5d6yRwRIMjqKyrXtAK5QCJE9okAfMSR6BajZCVnyBHr2kEj66T9D886wFsai3MgDtgk7fP8ASs6oFKUoUVqeJPFsEb61I9VDOv8A9lB+VbdV3jLeUD1PzEL+jGhGWdxw0MuzAEehGKxqu8M45CBbLecAsAZkqTuOWGkfKrGtS7Mw6FKUrJsUrxWBAIyDkHqK9oBSlKAUpXisCJBBHagPaUpQClKUApSlAKUpQClKUApSlAKwQrPlnAGuZ+PJaJ5QV2xERWdYPcC7n+v2FCGdKjtXNX4WA6sIn5b/AFFZswGTQp7UN92XzQCo+IfiA6jrHT78jmlwEkDcRIIIMHYweWDnselZ0IEUsQE0knaTA2J3APTpXintBkgg8iDBH1Fa/BDSPL5dLEAjqMMR6tqn1IrYk5JiSSTAgZM7SaiCPagZP2iHoj/cp/tU9RXLkbCTtA6nlP8AfXYEisGIQ+8Lfh0AHudRj6An6ip6xQGPNE9tvQf39Nqhe4WZVQ4I1Ft/KDEDuTz6T2oDYpSqrj/EdN21aU5ZxqjmBMj0n745EAUsmeCBnM55Y5Huc/Q1SeMMRcYSfgUxyEuB/wDk1bcW6qssJ6DqZBj0kA/y9qwscEHPvbgDEqAF3UKCSJH4jJOT9BWZyUVbNRi2csygcVw1zmHKEjkrCRP5ZB+ZrrXe2wguPk+n7qQa20UAQAAOgwKmsvmP6muUv5Nvo0sdeSn0XFGq2+tf3WIP+l95/impLfEC4rBTpaCIO6naY5wf7Bre45Le7of41IDD5ghvln0rn+JJFzUj6h+9GltsTiCRttkbk4Fdscua6MuBc2k0qF6AD6VnUHDX9azz5j++VT1oyKV4rA7Gdx8wYP3r2oDF0kEdf7516B65M5JJn1NanF8etvA8zdBy9Ty/XtTwpLlybtw4OEAwsDcgc5PMztjBqpEtG5SpSnyH3qOaNUE7PKUpUKKUpQClKUApSlAKVH7zzaYO0k8htA9TM/L0rB7+Doy0lQDtI3J/KN/tuRQC9fg6VGpzy5AdWPIfrQWASC2SM9p69z9hyArxQtpSWMycscs7HAwNycAKOwArV4nxZUMRqPNRHl6y209hO29SvLIWVYss/L/tNax8QthQ7HSp2MEg+hA/WKnt3laQDkdQR+oopJ9C0abXCLiBomSsjGpGBI/mDKPuRG1WFQcQMp3YD0/FI+SkehNGb9oo/IxP+pI/Q0QRJbTSI9T6kmSfqTUPHcYtoAsCZMACJ7nJAgf1rZqoa1768SfgtnT/ABMMsPrE9lHWqCdOLd91KCARO5BmM8tjiJ2zUnCOXYn8KkqOQn8UD1584HQzK+kFicnSoUdWYsAPrFa7v5AB8Gw63Dz/AJSZ9fTfi36nfjox07ZJfcudCGAd2H7vOD9p7/OpbPDHX5Co8mEOBiAYI2AAXEc6rf8AxrhrZZWuLrB84J0EESIOuNs433xVX4h7ULE21ZyGgFdSWww3m4QGYgThREGunGTXzNx+Z03FJdGFRu5BX6KSefU7fpzvAeAcSb63rhRYMhQSxECAoAAED1rX4X234hRFxLb94KH5wSPtVr4J4/c4wvCKiIANQLNJacDbYCee4qccyvr6m7j0ecXcYk/jbZQOZ/dGMZ59pO01Jx/H3LKhSyYCwY6kiJJgwB0qwscKqGclv3jEx0EAAD0Aqk9reC94EaYCo+rEyPJy5kZ+vaukqlSaHLejWHtG6/HdEzgBQOw3XJ/3jNdRwXFubaN5DKg5GZjI1Axv2r5y/h1n3cSwkDsI7TOP77V2Hs7fZLYs3AVdAzKxjRctliyujDBAUiRuMVJY4exLfaZdX+PcfgUjmCcf6to9QKpLxGowukT8O8dq1+K9qrRSbTTOxgyfRWED1bb91q57w3xK6byoAXFxoCDcHnpJPSWM4wxMb1rElC9GlP3Ow4B41tOFCkzsASQx+gk/wirJHBypB9DP6Vr8dwws8JeLHPu3LEddEQPsB9dzXzngjxN6W1uqKDquEu0RuFEyzflH2kViD5tvxZJtJo+kIq2lOpsTj6QAANzjlvVdxfiJMqvlHY5+ZG3oM9xtVJwbCITXGxuOZuN1A5KMcsVH4r4gti3IgscIv9T2E/Pauije2YfdIu/DuEFxpc6UX4uU89Ij7xsD3FdOt1IlIjYRgCMR222qk8H4cHh7LOSWa2jNy8zKC222SasFUAQBAHKtycYo88eUn7Ike5NYUpXJuz0JUKUpUKKUpQClKUAqO9c0ifkO5O22fp3qStHiFZy0fhGlT/6jD4vRQR89XSjITcE2pA0Rq2neORMYkyTjAmNgKaUtBnJgZJJ7mT9TA+SjlU6KAABsBA9BtXNeN8Ybr+7UwinJHPkT+qj+Y9Ki0qHR5xXHs7atjkL+RTgx+cjduQwIMxpcPwz3WCJhfpI6yPhX0yeWN5LVkuwRRJP0+fYV1HB8KtpdK5PM8yev/SqQ1+E4FLaGycoykiYABG8AfCuQRz3zTg006BqkjVbIO8ASpPqEU/Ovb3ELc1KvmCg6m/CCQQFnmcgmMD7VKEyh5lm+ZCaD+k155RqejNbNkjbt/wBv61p33YMSolj5V3gbzMfmU/3vtu0Cd+3U8hVb4z4kvDKfxPpCqP3mEkse3mBNdr2kbLK4CQQpg8j07xWNiyqKFUQAPX5k8z3rn7vtTbQgOxUlEeChYj3ltXjUpAMao2G3PetDiPatnxbU5xLn3ajoYQlo/mFdOLq6Gi/8SuKql3MLqzicAaGkdBE95A/FXJe0HGO7G0x1IsZnUHHxKwPNDhh8sCIrW8V49rzzJCLCouwCqIUkfvGST3PYVomBJ+tSGOnyfYUTZ4+BcRmAYtbRiGyDC+7zzPwAnOZrzieKuX7hKhnOwUEsEWcDUxwO5OTJNPErBfiDaCl2REtwoJ8yW1FwQPzl96wuW7lo+7MoRyQyAfQbfzAHtXbSokdmV/wa/qKkppEefUFWYBKmTIZZjIG04mur8H43huEsrb1hju7KUOpzuYVjjAA7AVyFy4zGWJY9Tk9T981DqZmFu2NTsYAHU/38qNSlrwGorZ9J8N8Xt8Rq90rHSyqSdIGptucgdTEUHDF7rMFRlMAknShwJKtGpjyJIjygQN6ruD9njathUdEMftLgRNbZDMNTKTpDKhySIQCB8VRcH40lx3W2Hu+7Ki3rcgXHaQvlA0qixMkT5lwMT5sibT4m4tJpPtnZqiW0hQiL2AVftVFfvWnD2UQOuhjoVCVck5UEYQ5mc7kgSM7/AAV9QBrbXc6xCgxn3akmF9NRiJJrYvoTgwDkiD8MfiBwRHXFeSLp7R1fRwQ9mbZskW7um8ig/HrVyF84hVGnTpJ5kEsNlFdJ7K+Cpw66z5rjjLkZ09AOQO8ekzArX8SKiLiqA7aUdhKlUymy40tJkYwUEgqorQTxG+NKl2WcAqoYH/Upj6DmYFe2SlONJnJVHbRbe1qvctNbYhFaNKyCzkEHU0YCiMCcmJIMRxycU63FsEaVUgBdoGM4xtJkY7Zmrguza9Ta3zDmZBiVV2nInlyB5YNUfilr9ob4aFCrHMkkaTM8tJx39M6xwcVTJyt2XHG8WlpNTeiqN2PICqngPBb3E3PeXJUyDAjyxlQZkKO2Sd451XcR4kxOpZ1f8Q/FHRBkIPST3mr/APw+vw98E4IQ5P4tTDc8zq+wrpJOrOMcycuK/J1/AcJ7pQNTNAAAJOkAbAAn7/pW3SlcTsKUpQClKUApSlAKUpQCoQVtpLMAAJZiYE7sSTtkk/Opqrrye9uaT8FuJHJnIkA9gCPrQg8T44Lb8h8z4G4IESWg52Ig9xXOKsbf3/Yq8uot13tAAOmbZ67a07Lq/qdhFUbuqkC4dImDODEwwE/iAnFZUrdeSdnQeC8MET3jbsJ9E3H13+nSniN1202kwziW/KvT0wZ/h71V8d7V2gNKAkfQR0ztjGxql4n2huXH8iqHYaQfic5wAT5RnoBXRQk+kXXudb/mbKoLVtwTAgCSSC+lnwMgENMc1IrPivELdsqzkKoLxqITJ0CfMQd9ewPOvnPiIa7cC20LrbRbY0gtOmSzY5M7Ow7EVm/hvEuqabNwIiAL5GG5Nxjkc3djPSK08KbVs5837H0DwzxVbzsUkpbgkgEKWM6VBbJiC0gDYbzXI+1HEl+JuGSQh0gbxoUKwA/iBrpPAuHHD8OuvGlTcuesamHyUBfl3rh7PDcRdOsKzM7lhAMzJJf+EPGTicda5YknNyT0tFjK2RcTfHvWJgwFSRn/AMtVtgg9IXHaKibiR0NXVj2O4hsEohidLN5gNtkDVA/szd957pWRmmMFomJO68s/Q17OWPyzPqRXDihzBrf8PdQTdYSlsayOTMD5Lf8AM+kH8uo8q1uI8E4hLgtsnmMwQQVIABJ1TAA1LvG46irFfZninQKiaUGZchS7RGrSfMAASACAYJOCxFJRhXYUpFFavO1zXrIfVqLklDqJktOMznFWN/hlW2Ga6gc6SqLJGnnJCkmegAG/mMRW1xPstxmmAiNH7rID6knO0fTaqPiOEuWzpuKUboVI9TmJ9aqcZPTJuKJAdW9xVHcXNu2lD94rufY/g+EUM9t/eXAPMzDSUB/dU7KesnmJ5Dh/C+Be9cFpWUMwOnVIBKgsVMTHlDGe1XPh3A3+E4lDcRgjsLbMMoVuEJll2yVMGD5aZEmqTEW7to6r2r41BwdyHALgqoOC8Ea1AP5ZHTNVfhnhmuzbIlEK/GQpUSjMhuTk651QMAOqnGW5r2h8R99fdp8iylscginf+Yy3z7Ve+znjtmxaC3rnEu6OALIJK6V8whWIUZmdR5DAEVw4tLRIZk8j/B03A+DXrbH3d6bThdACqpXykklgpJEYExEjJEAQHiL4uf5dLNzSGHvGBV9c7Fmd1ZgdzGwAHwyByvBe3N/hlK+71gu7wzfDqJcKDyGXxJwMRmun4/2pX3a3PeqiOSBoU+8cK2hioJ+FSTkEElSBiTXneCSltHqWRV2UnjXiDWrrWoe67XWK4l2WTPlSJAYHBETkbSck4ouMhreJOoMrRE/iAI6bbg9Km8O8bsre91bVri3NQkXGll0nSLmoADA2BMREAYrDxe0Sw94wgp51IEgAbMQSNJlsevWvTCLS2PTIoeP8aLDRYEIOexb/AGB671ocTxFy4A7YWYCj4Rj9e/rtUF23ct6ioKozMATGxmB1yPnv61PxbodItzpAEyCCDERnf/rzons4/wAiUYwa/wBNZmrs/wDDtARfPkJOhTqGohSGyBMDnkg7H0rjYrO3cZTKMynqrFT9QZrUlao8OPLGL0j7KBXtfL+C9puKtkH3hcc1ueYH+Y+YfI/WvpHh/FC7bS6AQHUNB3E7j5GuMotHtx5VPo2KUpWTqKUpQClKUApSlAQ8RdKiQjOei6Qfq7KPvVHw/E8aQBbs2094GuarlySdRB2VTEa1EZwK6B1kEdRFQcNaeQz6QQukBSSIxJkgbkD5AVbIzjuCbiRfS5cdI1+ZUkglyVJlhP4p6Yqw8T9n0vcRrLlA6S0CZdSqTvzDL8waisr50Xq6r8ywH610niXClULA7T6jUCqn5EqflXGc3Gaa+hza2cs/stYW9btlnYMCWyByaIx1UVb8B7PcKjlkTV5QPMZEMMmOcgxBxUXid6LyXOiI0ejsxH0rZ8D4jUSs/gSPRZX9NNd+cn5NpItGQABQAF2IGMdABUfFPPkHq3p0+f6T1FTOx2USx2H9T2rVVQGInVGWbqT/AH8hHKuGXJSpdmZuj1R+sn5EQPUtpjrBFecJwvu3eAqrjC8zpGDj8O0jeR0io+HuAspuAhSxlecQ0T27bDU3aN1RpAAx6mY5sxJ3jJJO/qamLS+Qh0Q8UVANzcoD0yQCAp+bEfOqLw0HWTMkjTPPU7AFvUL7w/Wt7xNyLKqRBZ8iZxLPOwOSATjnFaHhRPvYEYUNnafMg+guNXVv3NNl6uka2YDDyJjBCASJ28oJnkCeVYFneDlVPwqJDN3J3C88Ry+KYrG3kHUBnac6UBkT3gLPpzgVuW1IJ1SGBgg7rzA9TMk9T0iucZuTpEUrIrXCRlyWPTZB2CDB9TJ9Nq94vhEuobbqCvcTHcDr6Qe4pabVlmKqXwQCYEQpMZjXvtg5gSaltvKg9QD9RNdUzRyXB+zhscbauJJt+c9Sje7ZdJPMZwecfXqeNcrbdhuEYj1CmPvWbKBBkKBOSYGeRnGTA7TWynD/ALN7j4AVioYHeD5mXf0Hz3iLKbfZEkujgvZX2bD2gXUEuUZHQk3EClHMA+QAiRqPPFWXtP7L3LjrcUWUYx7x9jqZlCsSq+ZiS84ECBJER1PsvBtlgqKTAhF0qEAhV07gg6xBzjNWPF8LrVxg6lGDtqUysxymPpT4rjOzKhFao+Of+F3HuHhES4xRz7y4yFBLEobhDnyoq/AN2hiB5oF57R+yjOH4m1eS4zaZtgCR5QIVwxBMDoAetdxfs3LhRWCjA1TlwvOWU6fNt9SBiuP/AMR/FdJS1bI1ENrII1KsAaY3AbVv+QgZBjSyOUkiyhHjbPnQGvY+XHz5mOXT71K3EvpKamZOYz89t/SrTwbwV+IDkNoVBliJz+79JJ6Y6itHjeDuWbhtXF0svLkRyKnmp6/1BFdrp0eRRm9vSfRbeHeMggre8ysI1QGB/jXmNvtgVLxngYca+HIIOfdzM/8Atud/Q53ya523sK2+C4q5baLZMsY0xqDHl5eZ9M0lCtoy5p+mSte/k12MEg4IMEHcEbgjka81V09z2U466nvriokf8RtLtqIiQqkjJgBoiTOK57iuCuW7nunQh+S76h1WPiHcUjJeSvCvBjw6a3VC6oGYAs2FWTGo9hX2DhbC2raW0+FFCj0UQJNfOvCvZO9chrv7FMZbDmeQU7Hl5o9DX0azwmhV0qwVFVVJYqAFELBYiT3E1jJJPo9GGHFMknMZmAcgjBmD9jXtYWzImCs8jE+pjrv16xtWdcTsKUpQopSlAKUpQCsdeY7T9In9R9ayrV41X8rpkqZK9QcH7f3gUIadrw8rxGsrKSXEEfEcwQTyaT/p7xvcVxOpHVvJsRPMA6jkSJgVLauBgCpkVFw1wPbVmjbzTEBl+L6EGuc8abuzLjZS+K2Cq2ydypB7H4gPoW/01D4JbYXAUjSJUk/CFzA7kGMDoNt6vWtpd1BwGTywDyIkz2MEfIjuK9fgxEIxTEYho6Rqn/btVlbXpDvwZcTxQT9lb8918AdPzORsozgeg5mvbXCqqxqOlfic7u53P1Ow9ByrR4TgmtST5nbdxk57HI5de5gY31VhAueUjZTGJxOME5iRI5Dv5/hycq/ZmrZHCoNT7nA/e7BY/Ee39JpZtEmWlQeQgx0lREjmYIJIk9K2RnIrR4riFf8AZoQzTJGpYhYJnDdt1IJ6gMK9PFJfI3SRB7QqQqHBWT5lMjliNwcHBHKtXgLAVDeY/lRebkkD7mFHzq9TwJL1tS5dGLFyskAkalQMrjVAWBy27ma+/wAEyXAXI0pGlQsQxwpiTqEYBHXYRnGW6Mu2jaPDkMgnIhjgbj4d/wA2f5TWXHX92I1HAIAA1sYCrjcnEnkvSRXg4Xiy8lFhlmdQJUz8LKSIIWNtQnVW0vhbrDHS5GwByCd41QCT1Mb/AFRi1Gl9wtaRppdKoCw1efSxEYO5xMncGYzM84raVSzaF+I9fwjmWG+OnoK0fEuOtKyWrz6SXGhGcowLSqmYJ06thEEgAdDeeHXdSyje9PwtcMAErIgKOhnGBk5JmustLSN2bFjg0TMS37zZbvnkOwgVXeIcS9wabekIWKhssWdVLBdIiFMYYnJjEEE7nEcE1wjXcbRmUUKFbGCd2wcxMHmKgtcPcS8sZUg6jiYg6QwkR5tmAPPAk1mMWnbJZU8Ez8PcC+d1KyJYFYLZCnl8aadUZJUEgyvQPxtsKW1AwDiQGkfhg5DYiDmoOOtppKnkPmpyUg8sah9KpLHCw1tGLkh0KEMdB0FnbWDPmIZiTz0rtBlOKe2aSssvFuK9xZe6xEiCxkDJIXEjlMAem9fHfFOKF+7rRGDMqggnUzvGWP2HooPWvpvtV4U/G2wiXlREfzrpLF2+EGQwgKdQiMnOIEw+zPsvw1hbjXYuOBBdxCqhXOlZOkmGzJONxNaw+lX5M5E3rwReB+FXLfDhFAwCXxMs3xTkT0gQQAMmtjifCbfGW1S5IfS2h1BlXUQ2Y+AkZDYPl5xXU8MgtAJyk6T15wfzfrHyFBxXDe6vs6eVtwREwYx3XAEHHlFdIvk2lpm/7Lj7dHD2PYm4DNy6gTcModnK7/AQADH5j1r6J7OezlnhUBRPORl2y8fuzy7gQJqj4/xa5wqG7btIwTU767jR53BItjMZLHzHyg6VkYF9wnin+Y4a1dTyC6is0EsUkeZZA3BkTjY7crJyrZx+Govon4ziS50JJGxjduoHRep/s5WvDAVhyYiNKErA/iEGfSPnWrdJXy2kbdSGPlBIbMgGSAAIWIzVjcuXB5UUsRgudKieZiZ+0etYNmj4r4cvu10sEZWHnYknYqYO8jVIA3IAxuJ+IZnkrbb1aAQOysZHyGacNwrl9V3SYgrpYnOQZkDbER17VZ1SWc/Sri/w6tkqCRtkjtkjl29Kpb1t1Ygsu/7pP31D9Ky0aTMqUpUKKUpQClKUApSlAYLbUEsAATuQMn161rDgFDlvwsZKywGrfUADE+onbOAK3KUIe2dK40gj5j5yOfc1Kz2zsrA9jI+4qGlARXQ5PkKherAkj5Awa2relFjV7yTkMOmxmMdowKipQC/w1u58cL3CS5AMgF2LT6kTvtNTeH8NYtmQWLH8TnbOB0EdY+dQ0oSi7e5Anl1EVpcWUcRkEZDaTjmRtsen9QK0aVmSb1f6NKkbVq9cXM6hHwtuP5x/UVrcb409q1cb3bsyqdAVS5Zo8oZLckZ5jGD8MgV5SswjKPbv7GpOL8Hx7xX/ADfE3GL27puO34g6xJwqhgAqrOM4A519atcSumyFVPLbCsTIfUoUBQwIaMPOGBx6GeayA7j712cmzCSRI3idsD4irjnIaezHp3IEVC94F/eK8sVAnECCTEcxk+uOlZ6D+U+pU/SdqxfhxqXUFXOf2kSSCAAIYkzGJA9djBoXL+okyMwSAcTUdm4mpi6tpWAr50hipLGVMzDL5oxByM1LxSQvkVtxJZHBjOAdEDMZg7bZrSXi7ttWkoUgFVCsCBJ1+Zpl/MrAHcq3XGJ3WjUUn2Y+FK6a1J8pgqDuVYSGmTIJBAOD5Sc1uNMEAwTA7RImRzESPnVSrMtyLYYISWfRbgEmcgNtmMDbq29W1pAy6tbKQdmQZEfuhpmYzPLbNbu9lkqZHwd+4lsq41KCIQgtC4jSwBOD2xGNoqa9xNvWuuyzA4kzcjnJBJGnvvJ+HnWLCNiG9QV/5q8UtzAHoSf6CsU07TJafg4b/E8W2NleHDyQ3vEQQkAjSXTEPM5iY3nFXfsH4hcHCIHnyEooJKjSNpXTneJkTFdBNK6ctEJeF4xyT724mkzhA0jpDAY/WpxxChlKMYzq1F4OMfFPPMxPfedOlSyPZbHih1T/AFH/AJaifiyQR5P/AJHH3CSKrqUsUT8NduKSWuIVJMKSzaenmKgn51DfMsTM968pQopSlQClKUApSlAKUpQClKUApSlAKUpQClKUApSlAKUpQClKUB6O4n6/0M1mtxQQQiSDIJliPQkyKUoQzu8Szbx9J/Wah1f3/wBq8pQClKUKKUpQClKUApSlAKUpQClKUB//2Q==",
            "Hanh dong"))
        myCatagoryList.add(Catagory("https://img4.thuthuatphanmem.vn/uploads/2020/02/16/top-anime-phep-thuat-hay-nhat_041041352.jpg",
            "Phap Thuat"))
        myCatagoryList.add(Catagory("https://anhdepblog.com/wp-content/uploads/2018/01/hinh-nen-anime-lang-man-47.jpg",
            "Tinh yeu"))
        myCatagoryList.add(Catagory("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJ_nbCLuism2n_BWrn9x1wtyqMCZcDhGlmCwdAdMq5n-MHfA803SK4eFYPpCsnFwVmTtk&usqp=CAU",
            "Comment"))
        return myCatagoryList
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        viewModelActivity = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
    }

    override fun ClickCatagoryListener(position: Int) {
        when (position) {
            0 -> arguments.let {
                Log.e("OK", "onCLick hanhdong")
                viewModel.create_Name_of_book_byString("hanhdong")
                setupFragmentforCatagory()
            }

            1 -> arguments.let {
                Log.e("OK", "onCLick phepthuat")
                viewModel.create_Name_of_book_byString("phepthuat")
                setupFragmentforCatagory()
            }

            2 -> arguments.let {
                Log.e("OK", "onCLick tinhyeu")
                viewModel.create_Name_of_book_byString("tinhyeu")
                setupFragmentforCatagory()
            }

            3 -> arguments.let {
                Log.e("OK", "onCLick comment")
                viewModel.create_Name_of_book_byString("comment")
                setupFragmentforComment()
            }
        }


    }

    private fun setupFragmentforCatagory(){
        activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(R.anim.left_in,
            R.anim.left_out,
            R.anim.right_in,
            R.anim.right_out)?.replace(R.id.contain_activity_layout, CatagoryDetailFragment())
            ?.addToBackStack("catagoryDetailFragment")?.commit()
    }

    private fun setupFragmentforComment(){

        if (CurrentUser.getUser()==null){
            Toast.makeText(context,"U need Login to see CMT",Toast.LENGTH_SHORT).show()
        }
        else{
            changeFragmenttoCmtFragment()
        }

    }

    private fun changeFragmenttoCmtFragment(){
        activity?.supportFragmentManager?.beginTransaction()
            ?.setCustomAnimations(R.anim.left_in,
                R.anim.left_out,
                R.anim.right_in,
                R.anim.right_out)?.replace(R.id.contain_activity_layout, CommentFragment())
            ?.addToBackStack("commentFragment")?.commit()
    }

}